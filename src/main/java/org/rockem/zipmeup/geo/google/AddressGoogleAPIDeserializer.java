package org.rockem.zipmeup.geo.google;

import com.google.gson.*;
import org.rockem.zipmeup.geo.Address;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class AddressGoogleAPIDeserializer implements JsonDeserializer<Address> {

    private static final List<AddressComponent> COMPONENTS =
            Arrays.asList(new CityComponent(), new StreetComponent(), new HouseNumberComponent());

    @Override
    public Address deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Address.AddressBuilder ab = Address.builder();
        extractAddressComponentsFrom(json).forEach(element -> {
            COMPONENTS.forEach(component -> {
                component.appendIfNeeded(element, ab);
            });
        });
        return ab.build();
    }

    private JsonArray extractAddressComponentsFrom(JsonElement json) {
        JsonElement results = json.getAsJsonObject().get("results");
        return results.getAsJsonArray().get(0).getAsJsonObject().get("address_components").getAsJsonArray();
    }


    private static abstract class AddressComponent {

        public void appendIfNeeded(JsonElement element, Address.AddressBuilder builder) {
            if(element.getAsJsonObject().get("types").getAsJsonArray().contains(new JsonPrimitive(type()))) {
                append(element.getAsJsonObject().get("long_name").getAsString(), builder);
            }
        }

        protected abstract void append(String value, Address.AddressBuilder builder);

        protected abstract String type();
    }

    private static class CityComponent extends AddressComponent {

        @Override
        protected void append(String value, Address.AddressBuilder builder) {
            builder.city(value);
        }

        @Override
        protected String type() {
            return "locality";
        }
    }

    private static class StreetComponent extends AddressComponent {

        @Override
        protected void append(String value, Address.AddressBuilder builder) {
            builder.street(value);
        }

        @Override
        protected String type() {
            return "route";
        }
    }

    private static class HouseNumberComponent extends AddressComponent {

        @Override
        protected void append(String value, Address.AddressBuilder builder) {
            builder.houseNumber(value);
        }

        @Override
        protected String type() {
            return "street_number";
        }
    }


}
