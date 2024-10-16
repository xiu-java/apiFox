/**
 * Copyright 2017 SmartBear Software
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.apifox.model.openapi.v3.models.media;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * UUIDSchema
 */

public class UUIDSchema extends Schema<UUID> {

    public UUIDSchema() {
        super("string", "uuid");
    }

    @Override
    public UUIDSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public UUIDSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public UUIDSchema _default(UUID _default) {
        super.setDefault(_default);
        return this;
    }

    public UUIDSchema _default(String _default) {
        if (_default != null) {
            super.setDefault(UUID.fromString(_default));
        }
        return this;
    }

    public UUIDSchema _enum(List<UUID> _enum) {
        super.setEnum(_enum);
        return this;
    }

    public UUIDSchema addEnumItem(UUID _enumItem) {
        super.addEnumItemObject(_enumItem);
        return this;
    }

    @Override
    protected UUID cast(Object value) {
        if (value != null) {
            try {
                return UUID.fromString(value.toString());
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UUIDSchema {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
