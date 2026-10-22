/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EnumListFormFieldTableComponentTypescriptRenderer"
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
import {FormArray, FormControl} from "@angular/forms";
import {
    SingleFormFieldTableComponent
} from "@app/shared/form-controls/single-form-field-table/single-form-field-table.component";
import {
    AppellatioComisSelectorComponent
} from "@app/enum/appellatio-comis-input-selection/appellatio-comis-selector.component";
import {AppellatioComisEnum, AppellatioComisEnumValues} from "@app/wto/appellatio-comis.enum";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

@Component({
    selector: 'app-single-appellatio-comis-form-field-table',
    templateUrl: './appellatio-comis-input-table.component.html',
    standalone: true,
    imports: [
        SingleFormFieldTableComponent,
        AppellatioComisSelectorComponent,
    ]
})
export class AppellatioComisInputTableComponent {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    createControl = (): FormControl => new FormControl<AppellatioComisEnum>(AppellatioComisEnumValues[0], {nonNullable: true});
}
