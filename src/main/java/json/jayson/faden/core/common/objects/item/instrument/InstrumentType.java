package json.jayson.faden.core.common.objects.item.instrument;

import json.jayson.faden.core.registry.FadenCoreRegistry;

import java.io.Serializable;

public class InstrumentType implements Serializable {

    private String typeId = "";
    public InstrumentType(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

}
