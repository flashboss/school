package it.vige.school.rooms.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class RoomsSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "rooms";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return RoomsService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return RoomsServiceProviderFactory.class;
    }

}
