/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EnumSelectorComponentTypescriptRenderer"
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
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {CommonModule} from '@angular/common';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {AppellatioComisI18nComponent} from "@app/enum/appellatio-comis-i18n/appellatio-comis-i18n.component";
import {MatOption} from "@angular/material/core";
import {MatSelect} from "@angular/material/select";
import {AppellatioComisEnumValues} from "@app/wto/appellatio-comis.enum";
import {FieldErrorMessagesComponent} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";

@Component({
    selector: 'app-appellatio-comis-selector',
    templateUrl: './appellatio-comis-selector.component.html',
    styleUrls: ['./appellatio-comis-selector.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        AppellatioComisI18nComponent,
        MatOption,
        MatSelect,
        FieldErrorMessagesComponent,
    ]
})
export class AppellatioComisSelectorComponent {
    @Input() label: string = '';
    @Input({required: true}) enumFormControl!: FormControl;
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    protected readonly appellatioComisList = AppellatioComisEnumValues;
}
