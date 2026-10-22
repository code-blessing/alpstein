/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityFormComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-form.component.scss`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-form/opus-magnum-form.component.scss`
 */
object EntityFormComponentScssRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |.edit-form-container {
          |  margin: 0 auto;
          |  padding: 20px;
          |
          |  mat-card {
          |    .mat-mdc-card-header {
          |      margin-bottom: 20px;
          |    }
          |  }
          |}
          |
          |.form-actions {
          |  display: flex;
          |  justify-content: flex-end;
          |  gap: 12px;
          |  margin-top: 32px;
          |
          |  button {
          |    min-width: 100px;
          |  }
          |
          |  .save-button-spinner {
          |    display: inline-block;
          |    margin-right: 8px;
          |    vertical-align: middle;
          |    // The button keeps its label colour while it is disabled, so the spinner
          |    // follows that colour instead of the theme's primary.
          |    --mat-progress-spinner-active-indicator-color: currentColor;
          |    // A determinate spinner draws exactly `value` percent of the circle, so the
          |    // visible arc keeps the same length: the indeterminate mode shrinks it to a
          |    // barely visible sliver twice per turn. Turning the arc is then up to us.
          |    animation: ${model.entityName.kebabCase}-save-button-spinner-rotate 1.2s linear infinite;
          |  }
          |} 
          |
          |// Angular does not scope keyframe names to the component, hence the prefix.
          |@keyframes ${model.entityName.kebabCase}-save-button-spinner-rotate {
          |  to {
          |    transform: rotate(360deg);
          |  }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form.component.scss"
    }
}