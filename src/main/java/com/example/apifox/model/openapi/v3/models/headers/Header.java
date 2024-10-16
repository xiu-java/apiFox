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

package com.example.apifox.model.openapi.v3.models.headers;

import com.example.apifox.model.openapi.v3.models.examples.Example;
import com.example.apifox.model.openapi.v3.models.media.Content;
import com.example.apifox.model.openapi.v3.models.media.Schema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h4><a name="%3Ca-name=%22headerobject%22%3E%3C/a%3Eheader-object" class="md-header-anchor"></a><a name="headerObject"></a><span>Header Object</span></h4>
 * <p><span>The Header Object follows the structure of the </span><a href='#'><span>Parameter Object</span></a><span> with the following changes:</span></p>
 * <ol start='' >
 * <li><code>name</code><span> MUST NOT be specified, it is given in the corresponding </span><code>headers</code><span> map.</span></li>
 * <li><code>in</code><span> MUST NOT be specified, it is implicitly in </span><code>header</code><span>.</span></li>
 * <li><span>All traits that are affected by the location MUST be applicable to a location of </span><code>header</code><span> (for example, </span><a href='#'><code>style</code></a><span>).</span></li>
 *
 * </ol>
 * <h5><a name="header-object-example" class="md-header-anchor"></a><span>Header Object Example</span></h5>
 * <p><span>A simple header of type </span><code>integer</code><span>:</span></p>
 * <pre><code class='language-json' lang='json'>{
 *   &quot;description&quot;: &quot;The number of allowed requests in the current period&quot;,
 *   &quot;schema&quot;: {
 *     &quot;type&quot;: &quot;integer&quot;
 *   }
 * }
 * </code></pre>
 * <pre><code class='language-yaml' lang='yaml'>description: The number of allowed requests in the current period
 * schema:
 *   type: integer
 * </code></pre>
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#headerObject"
 */

public class Header {
    private String description = null;
    private String $ref = null;
    private Boolean required = null;
    private Boolean deprecated = null;

    /**
     * Gets or Sets style
     */
    public enum StyleEnum {
        SIMPLE("simple");

        private String value;

        StyleEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private StyleEnum style = null;
    private Boolean explode = null;
    private Schema schema = null;
    private Map<String, Example> examples = null;
    private Object example = null;
    private Content content = null;
    private Map<String, Object> extensions = null;

    /**
     * returns the description property from a Header instance.
     *
     * @return String description
     **/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Header description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the required property from a Header instance.
     *
     * @return Boolean required
     **/

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Header required(Boolean required) {
        this.required = required;
        return this;
    }

    /**
     * returns the deprecated property from a Header instance.
     *
     * @return Boolean deprecated
     **/

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Header deprecated(Boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    /**
     * returns the style property from a Header instance.
     *
     * @return StyleEnum style
     **/

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public Header style(StyleEnum style) {
        this.style = style;
        return this;
    }

    /**
     * returns the explode property from a Header instance.
     *
     * @return Boolean explode
     **/

    public Boolean getExplode() {
        return explode;
    }

    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    public Header explode(Boolean explode) {
        this.explode = explode;
        return this;
    }

    /**
     * returns the schema property from a Header instance.
     *
     * @return Schema schema
     **/

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Header schema(Schema schema) {
        this.schema = schema;
        return this;
    }

    /**
     * returns the examples property from a Header instance.
     *
     * @return Map&lt;String, Example&gt; examples
     **/

    public Map<String, Example> getExamples() {
        return examples;
    }

    public void setExamples(Map<String, Example> examples) {
        this.examples = examples;
    }

    public Header examples(Map<String, Example> examples) {
        this.examples = examples;
        return this;
    }

    public Header addExample(String key, Example examplesItem) {
        if (this.examples == null) {
            this.examples = new LinkedHashMap<>();
        }
        this.examples.put(key, examplesItem);
        return this;
    }

    /**
     * returns the example property from a Header instance.
     *
     * @return String example
     **/

    public Object getExample() {
        return example;
    }

    public void setExample(Object example) {
        this.example = example;
    }

    public Header example(Object example) {
        this.example = example;
        return this;
    }

    /**
     * returns the content property from a Header instance.
     *
     * @return Content content
     **/

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Header content(Content content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Header header = (Header) o;
        return Objects.equals(this.description, header.description) &&
                Objects.equals(this.required, header.required) &&
                Objects.equals(this.deprecated, header.deprecated) &&
                Objects.equals(this.style, header.style) &&
                Objects.equals(this.explode, header.explode) &&
                Objects.equals(this.schema, header.schema) &&
                Objects.equals(this.examples, header.examples) &&
                Objects.equals(this.example, header.example) &&
                Objects.equals(this.content, header.content) &&
                Objects.equals(this.extensions, header.extensions) &&
                Objects.equals(this.$ref, header.$ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, required, deprecated, style, explode, schema, examples, example, content, extensions, $ref);
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public Header extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && ($ref.indexOf('.') == -1 && $ref.indexOf('/') == -1)) {
            $ref = "#/components/headers/" + $ref;
        }
        this.$ref = $ref;
    }

    public Header $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Header {\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    required: ").append(toIndentedString(required)).append("\n");
        sb.append("    deprecated: ").append(toIndentedString(deprecated)).append("\n");
        sb.append("    style: ").append(toIndentedString(style)).append("\n");
        sb.append("    explode: ").append(toIndentedString(explode)).append("\n");
        sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
        sb.append("    examples: ").append(toIndentedString(examples)).append("\n");
        sb.append("    example: ").append(toIndentedString(example)).append("\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    $ref: ").append(toIndentedString($ref)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

