/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumI18nComponentSpecTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis-i18n.component.spec.ts`
 * - path: `enum/appellatio-comis-i18n/appellatio-comis-i18n.component.spec.ts`
 */
object EnumI18nComponentSpecTypescriptRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |import {${model.enumName.pascalCase}I18nComponent} from "@app/enum/${model.enumName.kebabCase}-i18n/${model.enumName.kebabCase}-i18n.component";
          |import {${model.enumName.pascalCase}Enum} from "@app/wto/${model.enumName.kebabCase}.enum";
          |import {ComponentFixture, TestBed} from "@angular/core/testing";
          |
          |describe('${model.enumName.pascalCase}I18n', () => {
          |    let component: ${model.enumName.pascalCase}I18nComponent;
          |    let fixture: ComponentFixture<${model.enumName.pascalCase}I18nComponent>;
          |
          |    beforeEach(() => {
          |        fixture = TestBed.createComponent(${model.enumName.pascalCase}I18nComponent);
          |        component = fixture.componentInstance;
          |    });
          |
          |${ model.enumValues.joinToString("") { enumValue ->  """    it('should display correct text for ${enumValue.screamingSnakeCase}', () => {
              |        component.enumValue = ${model.enumName.pascalCase}Enum.${enumValue.screamingSnakeCase};
              |
              |        fixture.detectChanges();
              |
              |        expect(fixture.nativeElement.textContent).toBe('${enumValue.screamingSnakeCase}');
              |    });
              |
              |""" } }    // checking if everything is translated
          |    Object.values(${model.enumName.pascalCase}Enum).forEach((value) => {
          |        it(`should translate ${"$"}{value}`, () => {
          |            component.enumValue = value;
          |
          |            fixture.detectChanges();
          |
          |            expect(fixture.nativeElement.textContent).not.toBe('');
          |        });
          |    });
          |});
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "enum/${model.enumName.kebabCase}-i18n/${model.enumName.kebabCase}-i18n.component.spec.ts"
    }
}