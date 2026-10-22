package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.Builder
import org.codeblessing.sourceamazing.builder.api.annotations.BuilderMethod
import org.codeblessing.sourceamazing.builder.api.annotations.ClazzPropertyModification
import org.codeblessing.sourceamazing.builder.api.annotations.ExpectedClazzModelFromSuperiorBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.SetAsValue
import org.codeblessing.alpstein.model.builders.ItemAttributeOptionsDsl
import org.codeblessing.alpstein.model.schema.ItemAttribute

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = ItemAttribute::class, alias = "itemAttribute")
interface ItemAttributeOptionsBuilder: ItemAttributeOptionsDsl {

    /**
     * All options are preset to `false` by [ItemBuilder.attribute] already,
     * therefore they are replaced here.
     */
    @BuilderMethod
    override fun options(
        @SetAsValue(
            alias = "itemAttribute",
            clazzProperty = "isNullable",
            modification = ClazzPropertyModification.REPLACE,
        )
        nullable: Boolean,
        @SetAsValue(
            alias = "itemAttribute",
            clazzProperty = "isMultiple",
            modification = ClazzPropertyModification.REPLACE,
        )
        multiple: Boolean,
        @SetAsValue(
            alias = "itemAttribute",
            clazzProperty = "customValidation",
            modification = ClazzPropertyModification.REPLACE,
        )
        customValidation: Boolean,
    )
}
