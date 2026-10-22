/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `TypescriptEntityViewRegistrationListRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `entity-view-registry.ts`
 * - path: `entity-view-registry.ts`
 */
object TypescriptEntityViewRegistrationListRenderer : UiEntitiesRenderer {

    override fun renderTemplate(models: List<UiEntityModel>): String {
        return """
          |import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
          |${ models.joinToString("") { entity ->  """import {${entity.entityName.screamingSnakeCase}_VIEW_REGISTRATION} from "@app/${entity.entityName.kebabCase}/${entity.entityName.kebabCase}-routing";
              |""" } }
          |
          |export const GENERATED_ENTITY_VIEW_REGISTRATIONS: ReadonlyArray<ViewEntityRegistration> = [
          |${ models.joinToString("") { entity ->  """    ${entity.entityName.screamingSnakeCase}_VIEW_REGISTRATION,
              |""" } }];
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(models: List<UiEntityModel>): String {
      return "generated-entity-view-registry.ts"
    }
}