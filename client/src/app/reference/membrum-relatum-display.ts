/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemReferenceDisplayRenderer"
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
        [ searchValue="membrum-relatum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="MEMBRUM_RELATUM" replaceByExpression="model.itemName.screamingSnakeCase" ]
        [ searchValue="membrumRelatum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="clavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";

/**
 * One reference to a MembrumRelatum, ready to be rendered: the display attributes of the
 * resolved MembrumRelatum, already filled with the fallback where the reference could not be
 * resolved.
 *
 * Shared by every place that shows such references, no matter whether the form holds a single
 * reference or a whole list of them.
 */
export interface MembrumRelatumDisplayRow {
    /** Identifies the referenced entry. It is never shown: a bare key says nothing to the user. */
    clavisPrimaria: UUID
    /* @tt{{{
        @foreach [ iteratorExpression="model.displayAttributes" loopVariable="displayAttribute" ]
        @replace-value-by-expression
            [ searchValue="descriptioExDistanti" replaceByExpression="displayAttribute.attributeName.camelCase" ]
    }}}@ */
    descriptioExDistanti: string
    /* @tt{{{   @end-foreach  }}}@ */
}

/**
 * The display attributes of a MembrumRelatum: the attributes that identify an instance for a
 * human reader. A reference to a MembrumRelatum is stored as its bare primary key, which tells
 * the user nothing, so every place that shows such a reference resolves it to the whole
 * MembrumRelatumWTO and renders these attributes instead of the primary key alone.
 */
export const MEMBRUM_RELATUM_DISPLAY_ATTRIBUTE_NAMES: ReadonlyArray<keyof MembrumRelatumDisplayRow> = [
    /* @tt{{{
        @foreach [ iteratorExpression="model.displayAttributes" loopVariable="displayAttribute" ]
        @replace-value-by-expression
            [ searchValue="descriptioExDistanti" replaceByExpression="displayAttribute.attributeName.camelCase" ]
    }}}@ */
    'descriptioExDistanti',
    /* @tt{{{   @end-foreach  }}}@ */
];

/** Shown for a display attribute whose reference could not be resolved. */
export const MEMBRUM_RELATUM_UNRESOLVED_DISPLAY_VALUE = '—';

/** Flattens the display attributes of a (possibly unresolved) reference into one row. */
export function membrumRelatumDisplayRow(
    clavisPrimaria: UUID,
    membrumRelatum: MembrumRelatumWTO | undefined,
): MembrumRelatumDisplayRow {
    return {
        clavisPrimaria: clavisPrimaria,
        /* @tt{{{
            @foreach [ iteratorExpression="model.displayAttributes" loopVariable="displayAttribute" ]
            @replace-value-by-expression
                [ searchValue="descriptioExDistanti" replaceByExpression="displayAttribute.attributeName.camelCase" ]
        }}}@ */
        descriptioExDistanti: (membrumRelatum?.descriptioExDistanti ?? MEMBRUM_RELATUM_UNRESOLVED_DISPLAY_VALUE).toString(),
        /* @tt{{{   @end-foreach  }}}@ */
    }
}

/**
 * Joins the display attributes of one reference into a single line, for the places that have
 * room for one line only (the typeahead suggestions and the search field that shows the picked
 * entry).
 */
export function membrumRelatumDisplayRowLabel(displayRow: MembrumRelatumDisplayRow): string {
    return MEMBRUM_RELATUM_DISPLAY_ATTRIBUTE_NAMES
        .map(attributeName => displayRow[attributeName])
        .join(' — ');
}

/** The single line label of a whole MembrumRelatum, whose references are all resolved. */
export function membrumRelatumDisplayLabel(membrumRelatum: MembrumRelatumWTO): string {
    return membrumRelatumDisplayRowLabel(
        membrumRelatumDisplayRow(membrumRelatum.clavisPrimaria, membrumRelatum)
    );
}
