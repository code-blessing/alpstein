/* @tt{{{

  @move-comment-backward
  @template-renderer [
      templateRendererClassName="TypescriptSideNavLinkListRenderer"
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
        [ searchValue="ENTITY_SIDE_NAVIGATION_LINKS" replaceByValue="GENERATED_ENTITY_SIDE_NAVIGATION_LINKS" ]
        [ searchValue="entity-side-nav-links.ts" replaceByValue="generated-entity-side-nav-links.ts" ]

    @modify-provided-filepath-by-replacements

        @replace-value-by-expression
            [ searchValue="OPUS_MAGNUM" replaceByExpression="entity.entityName.screamingSnakeCase" ]
            [ searchValue="opus-magnum" replaceByExpression="entity.entityName.kebabCase" ]
            [ searchValue="opusMagnum" replaceByExpression="entity.entityName.camelCase" ]

}}}@  */

import {SideNavLink} from "@app/side-nav/side-nav-list/side-nav-link.model";
/* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
import {OPUS_MAGNUM_SIDE_NAVIGATION_LINKS} from "@app/opus-magnum/opus-magnum-routing";
/* @tt{{{   @end-foreach   }}}@ */
/* @tt{{{   @ignore-text   }}}@ */
import {ENTITAS_RELATA_SIDE_NAVIGATION_LINKS} from "@app/entitas-relata/entitas-relata-routing";
/* @tt{{{   @end-ignore-text   }}}@ */

export const ENTITY_SIDE_NAVIGATION_LINKS: ReadonlyArray<SideNavLink> = [
    /* @tt{{{ @foreach [ iteratorExpression="models" loopVariable="entity" ] }}}@  */
    ...OPUS_MAGNUM_SIDE_NAVIGATION_LINKS,
    /* @tt{{{   @end-foreach   }}}@ */
    /* @tt{{{   @ignore-text   }}}@ */
    ...ENTITAS_RELATA_SIDE_NAVIGATION_LINKS,
    /* @tt{{{   @end-ignore-text   }}}@ */
]


