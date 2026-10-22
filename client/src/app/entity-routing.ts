/* @tt{{{

  @move-comment-backward
  @template-renderer [
      templateRendererClassName="TypescriptEntitiesRoutingListRenderer"
      templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
      templateRendererInterfaceName="UiEntitiesRenderer"
      templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
  ] [
      isList="yes"
      modelClassName="UiEntityModel"
      modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
      modelName="models"
  ]

}}}@ */
/* @tt{{{
    @replace-value-by-value
        [ searchValue="entity-routing.ts" replaceByValue="generated-routes.ts" ]
    @modify-provided-filepath-by-replacements
    @end-replace-value-by-value

    @replace-value-by-expression
        [ searchValue="opus-magnum" replaceByExpression="entity.entityName.kebabCase" ]
        [ searchValue="OpusMagnum" replaceByExpression="entity.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="entity.entityName.camelCase" ]
        [ searchValue="OPUS_MAGNUM" replaceByExpression="entity.entityName.screamingSnakeCase" ]
}}}@  */
import {Routes} from '@angular/router';
/* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
import {OPUS_MAGNUM_ROUTES} from "@app/opus-magnum/opus-magnum-routing";
/* @tt{{{ @end-foreach }}}@ */
/* @tt{{{   @ignore-text   }}}@ */
import {ENTITAS_RELATA_ROUTES} from "@app/entitas-relata/entitas-relata-routing";
/* @tt{{{   @end-ignore-text   }}}@ */

/* @tt{{{
    @replace-value-by-value
        [ searchValue="ENTITY_ROUTES" replaceByValue="GENERATED_ENTITY_ROUTES" ]
}}}@  */

export const ENTITY_ROUTES: Routes = [
/* @tt{{{ @end-replace-value-by-value }}}@  */
/* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
    ...OPUS_MAGNUM_ROUTES,
/* @tt{{{ @end-foreach }}}@ */
/* @tt{{{   @ignore-text   }}}@ */
    ...ENTITAS_RELATA_ROUTES,
/* @tt{{{   @end-ignore-text   }}}@ */
];

