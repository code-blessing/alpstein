/* @tt{{{

  @move-comment-backward
  @template-renderer [
      templateRendererClassName="TypescriptEntityViewRegistrationListRenderer"
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
        [ searchValue="entity-view-registry.ts" replaceByValue="generated-entity-view-registry.ts" ]
    @modify-provided-filepath-by-replacements
    @end-replace-value-by-value

    @replace-value-by-expression
        [ searchValue="opus-magnum" replaceByExpression="entity.entityName.kebabCase" ]
        [ searchValue="OPUS_MAGNUM" replaceByExpression="entity.entityName.screamingSnakeCase" ]
}}}@  */
import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
/* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
import {OPUS_MAGNUM_VIEW_REGISTRATION} from "@app/opus-magnum/opus-magnum-routing";
/* @tt{{{ @end-foreach }}}@ */
/* @tt{{{   @ignore-text   }}}@ */
import {ENTITAS_RELATA_VIEW_REGISTRATION} from "@app/entitas-relata/entitas-relata-routing";
/* @tt{{{   @end-ignore-text   }}}@ */

/* @tt{{{
    @replace-value-by-value
        [ searchValue="ENTITY_VIEW_REGISTRATIONS" replaceByValue="GENERATED_ENTITY_VIEW_REGISTRATIONS" ]
}}}@  */

export const ENTITY_VIEW_REGISTRATIONS: ReadonlyArray<ViewEntityRegistration> = [
/* @tt{{{ @end-replace-value-by-value }}}@  */
/* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
    OPUS_MAGNUM_VIEW_REGISTRATION,
/* @tt{{{ @end-foreach }}}@ */
/* @tt{{{   @ignore-text   }}}@ */
    ENTITAS_RELATA_VIEW_REGISTRATION,
/* @tt{{{   @end-ignore-text   }}}@ */
];
