/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemReferenceTableRowRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="membrumRelatum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="ClavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.pascalCase" ]
        [ searchValue="clavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
import {FormControl} from "@angular/forms";
import {MembrumRelatumDisplayRow} from "@app/reference/membrum-relatum-display";
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */

/**
 * One row of the MembrumRelatum reference table.
 *
 * A reference is stored as a bare primary key in the form, which is meaningless to the user. The
 * row therefore carries the display attributes of the resolved MembrumRelatum flattened out, plus
 * a back-reference to the FormControl that actually holds the primary key.
 */
export interface MembrumRelatumReferenceTableRow extends MembrumRelatumDisplayRow {
    formControl: FormControl<UUID>
}
