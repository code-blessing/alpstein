package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * Validates the whole [SchemaData] before it is converted into the renderer model, so
 * that a broken schema declaration is reported instead of generating broken code.
 *
 * Every aspect of the schema is validated by its own [SchemaDataAspectValidator].
 */
class SchemaDataValidator {
    private val aspectValidators: List<SchemaDataAspectValidator> = listOf(
        ItemNameUniquenessValidator(),
        ItemNamePascalCaseValidator(),
        ItemPrimaryKeyValidator(),
        ItemAttributeNameUniquenessValidator(),
        ItemAttributeNamePascalCaseValidator(),
        ItemAttributeExampleDataCategoryValidator(),
        EnumNameUniquenessValidator(),
        EnumNamePascalCaseValidator(),
        EnumValueUniquenessValidator(),
        EnumValuePascalCaseValidator(),
        UiItemDisplayAttributeExistenceValidator(),
        UiItemDisplayAttributeUniquenessValidator(),
        UiItemDisplayAttributeTypeValidator(),
        SearchResultViewAttributeExistenceValidator(),
        SearchResultViewAttributeTypeValidator(),
        AttributeBlockAttributeExistenceValidator(),
        DbItemItemExistenceValidator(),
        DbItemUniquenessValidator(),
        DbColumnAttributeValidator(),
        DbNameUniquenessValidator(),
        DbEnumEnumTypeExistenceValidator(),
        DbEnumUniquenessValidator(),
        DbEnumValueValidator(),
        DbEnumNameUniquenessValidator(),
    )

    fun validate(schemaData: SchemaData) {
        aspectValidators.forEach { it.validate(schemaData, ValidationPath.schemaData) }
    }
}
