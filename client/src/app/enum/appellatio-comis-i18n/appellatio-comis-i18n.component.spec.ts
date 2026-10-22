/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EnumI18nComponentSpecTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEnumRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEnumModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="model.enumName.pascalCase" ]
        [ searchValue="appellatioComis" replaceByExpression="model.enumName.camelCase" ]
        [ searchValue="appellatio-comis" replaceByExpression="model.enumName.kebabCase" ]

    @modify-provided-filepath-by-replacements


}}}@ */
import {AppellatioComisI18nComponent} from "@app/enum/appellatio-comis-i18n/appellatio-comis-i18n.component";
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
import {ComponentFixture, TestBed} from "@angular/core/testing";

describe('AppellatioComisI18n', () => {
    let component: AppellatioComisI18nComponent;
    let fixture: ComponentFixture<AppellatioComisI18nComponent>;

    beforeEach(() => {
        fixture = TestBed.createComponent(AppellatioComisI18nComponent);
        component = fixture.componentInstance;
    });

    /* @tt{{{
        @foreach [ iteratorExpression="model.enumValues" loopVariable="enumValue" ]

        @replace-value-by-expression
            [ searchValue="VIR_HONORATUS" replaceByExpression="enumValue.screamingSnakeCase" ]

    }}}@  */
    it('should display correct text for VIR_HONORATUS', () => {
        component.enumValue = AppellatioComisEnum.VIR_HONORATUS;

        fixture.detectChanges();

        expect(fixture.nativeElement.textContent).toBe('VIR_HONORATUS');
    });

    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    it('should display correct text for FEMINA_HONESTA', () => {
        component.enumValue = AppellatioComisEnum.FEMINA_HONESTA;

        fixture.detectChanges();

        expect(fixture.nativeElement.textContent).toBe('FEMINA_HONESTA');
    });
    /* @tt{{{   @end-ignore-text  }}}@ */
    // checking if everything is translated
    Object.values(AppellatioComisEnum).forEach((value) => {
        it(`should translate ${value}`, () => {
            component.enumValue = value;

            fixture.detectChanges();

            expect(fixture.nativeElement.textContent).not.toBe('');
        });
    });
});
