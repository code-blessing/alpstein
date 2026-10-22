/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceTableRowRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-table-row.model.ts`
 * - path: `reference/membrum-relatum-reference-table/membrum-relatum-reference-table-row.model.ts`
 */
object ItemReferenceTableRowRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |import {FormControl} from "@angular/forms";
          |import {${model.itemName.pascalCase}DisplayRow} from "@app/reference/${model.itemName.kebabCase}-display";
          |${ if(model.hasUuidPrimaryKey) { """import {${model.primaryKeyAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |""" } else { """""" } }
          |/**
          | * One row of the ${model.itemName.pascalCase} reference table.
          | *
          | * A reference is stored as a bare primary key in the form, which is meaningless to the user. The
          | * row therefore carries the display attributes of the resolved ${model.itemName.pascalCase} flattened out, plus
          | * a back-reference to the FormControl that actually holds the primary key.
          | */
          |export interface ${model.itemName.pascalCase}ReferenceTableRow extends ${model.itemName.pascalCase}DisplayRow {
          |    formControl: FormControl<${model.primaryKeyAttribute.typescriptAttributeType}>
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-table/${model.itemName.kebabCase}-reference-table-row.model.ts"
    }
}