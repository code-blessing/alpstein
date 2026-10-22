/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `TypescriptSideNavLinkListRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `entity-side-nav-links.ts`
 * - path: `entity-side-nav-links.ts`
 */
object TypescriptSideNavLinkListRenderer : UiEntitiesRenderer {

    override fun renderTemplate(models: List<UiEntityModel>): String {
        return """
          |
          |
          |import {SideNavLink} from "@app/side-nav/side-nav-list/side-nav-link.model";
          |${ models.joinToString("") { entity ->  """import {${entity.entityName.screamingSnakeCase}_SIDE_NAVIGATION_LINKS} from "@app/${entity.entityName.kebabCase}/${entity.entityName.kebabCase}-routing";
              |""" } }
          |export const GENERATED_ENTITY_SIDE_NAVIGATION_LINKS: ReadonlyArray<SideNavLink> = [
          |${ models.joinToString("") { entity ->  """    ...${entity.entityName.screamingSnakeCase}_SIDE_NAVIGATION_LINKS,
              |""" } }]
          |
          |
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(models: List<UiEntityModel>): String {
      return "generated-entity-side-nav-links.ts"
    }
}