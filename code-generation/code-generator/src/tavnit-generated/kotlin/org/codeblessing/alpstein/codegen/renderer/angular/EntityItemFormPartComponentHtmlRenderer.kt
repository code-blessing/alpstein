/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel
import org.codeblessing.alpstein.codegen.renderer.angular.EntityItemFormPartBlocksComponentHtmlRenderer

/**
 * Generate the content for the template `EntityItemFormPartComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part.component.html`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part.component.html`
 */
object EntityItemFormPartComponentHtmlRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |<div [formGroup]="${model.item.itemName.camelCase}Form">
          |${ if(model.tabs.isNotEmpty()) { """<mat-tab-group dynamicHeight>
              |${ model.tabs.joinToString("") { tab ->  """
                  |    <mat-tab [label]="'${tab.tabTranslationKey}' | transloco">
                  |        <div class="column-layout">
                  |${ tab.columns.joinToString("") { column ->  """
                      |            <div class="column">
                      |${EntityItemFormPartBlocksComponentHtmlRenderer.renderTemplate(blocks = column.blocks, item = model.item, entity = model.entity)}
                      |            </div>
                      |""" } }        </div>
                  |    </mat-tab>
                  |""" } }</mat-tab-group>
              |""" } else { """""" } }
          |${ if(model.noTab.isNotEmpty()) { """<div class="column-layout">
              |${ model.noTab.joinToString("") { column ->  """    <div class="column">
                  |${EntityItemFormPartBlocksComponentHtmlRenderer.renderTemplate(blocks = column.blocks, item = model.item, entity = model.entity)}    </div>
                  |""" } }</div>
              |""" } else { """""" } }</div>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.component.html"
    }
}