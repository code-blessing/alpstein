/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EnumI18nComponentTypescriptRenderer"
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
import {Component, Input} from '@angular/core';
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-appellatio-comis-i18n',
    templateUrl: './appellatio-comis-i18n.component.html',
    styleUrls: ['./appellatio-comis-i18n.component.scss'],
    standalone: true,
    imports: [
        TranslocoPipe
    ]
})
export class AppellatioComisI18nComponent {
    @Input({ required: true }) enumValue!: AppellatioComisEnum;

    /* @tt{{{
        @foreach [ iteratorExpression="model.enumValues" loopVariable="enumValue" ]

        @replace-value-by-expression
            [ searchValue="VIR_HONORATUS" replaceByExpression="enumValue.screamingSnakeCase" ]

    }}}@  */
    protected VIR_HONORATUS: AppellatioComisEnum = AppellatioComisEnum.VIR_HONORATUS
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{   @ignore-text  }}}@ */
    protected FEMINA_HONESTA: AppellatioComisEnum = AppellatioComisEnum.FEMINA_HONESTA
/* @tt{{{   @end-ignore-text  }}}@ */
}
