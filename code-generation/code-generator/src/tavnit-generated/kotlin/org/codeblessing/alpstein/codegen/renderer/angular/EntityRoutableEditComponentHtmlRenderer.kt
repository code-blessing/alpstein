/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityRoutableEditComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-routable-edit.component.html`
 * - path: `opus-magnum/opus-magnum-routable-edit/opus-magnum-routable-edit.component.html`
 */
object EntityRoutableEditComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |<div class="${model.entityName.kebabCase}-routable-edit">
          |    @if (creating || selected${model.entityName.pascalCase}) {
          |        <app-${model.entityName.kebabCase}-form
          |                [${model.entityRootItem.itemName.camelCase}]="selected${model.entityName.pascalCase}"
          |                [saving]="saving"
          |                (save)="onSave(${"$"}event)">
          |        </app-${model.entityName.kebabCase}-form>
          |    } @else if (loading) {
          |        {{ 'text.loading' | transloco }}
          |    } @else {
          |        {{ 'text.itemNotFound' | transloco }}
          |    }
          |</div>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-routable-edit/${model.entityName.kebabCase}-routable-edit.component.html"
    }
}