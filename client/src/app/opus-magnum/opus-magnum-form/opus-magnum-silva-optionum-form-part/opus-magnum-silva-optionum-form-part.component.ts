/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityFormViewItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui.entityform"
        modelName="model"
    ]

    @replace-value-by-expression
      [ searchValue="OpusMagnum" replaceByExpression="model.entity.entityName.pascalCase" ]
      [ searchValue="opusMagnum" replaceByExpression="model.entity.entityName.camelCase" ]
      [ searchValue="opus-magnum" replaceByExpression="model.entity.entityName.kebabCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.item.itemName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.item.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.item.itemName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {FormArrayEditState} from "@app/shared/form-controls/form-array-edit-state";
import {FieldWrapperComponent} from "@app/shared/form-controls/field-wrapper/field-wrapper.component";
import {
    OpusMagnumSilvaOptionumFormPartValidationService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-validation.service";
import {OpusMagnumSilvaOptionumFormPartFieldName,} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-field-name";
/* @tt{{{   @if [ conditionExpression="model.item.containsUuidAttributes"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.item.containsTextAttributes"]   }}}@ */
import {TextInputComponent} from "@app/shared/form-controls/text-input/text-input.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.item.containsBooleanAttributes"] }}}@ */
import {BooleanInputComponent} from "@app/shared/form-controls/boolean-input/boolean-input.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.item.containsNumberAttributes"] }}}@ */
import {NumberInputComponent} from "@app/shared/form-controls/number-input/number-input.component";
/* @tt{{{   @end-if }}}@ */
import {DatepickerInputComponent} from "@app/shared/form-controls/datepicker-input/datepicker-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {OpusMagnumSilvaOptionumFormPartGroup} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-group";
import {TranslocoPipe} from "@jsverse/transloco";
/*
@tt{{{
    @if [ conditionExpression="model.containsNamedSectionSplitBlocks()" ]
    
}}}@
 */
import {SectionSplitterComponent} from "@app/shared/blocks/section-splitter/section-splitter.component";
/*
@tt{{{
    @end-if 
}}}@
 */
/*
@tt{{{
    @if [ conditionExpression="model.containsTextBlocks()" ]
    
}}}@
 */
import {TextBlockComponent} from "@app/shared/blocks/text-block/text-block.component";
/*
@tt{{{
    @end-if 
}}}@
 */
/* @tt{{{   @if [ conditionExpression="model.item.containsTextListAttributes"] }}}@ */
import {
    SingleTextFormFieldTableComponent
} from "@app/shared/form-controls/single-text-form-field-table/single-text-form-field-table.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.item.containsNumberListAttributes"] }}}@ */
import {
    SingleNumberFormFieldTableComponent
} from "@app/shared/form-controls/single-number-form-field-table/single-number-form-field-table.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.item.containsBooleanListAttributes"] }}}@ */
import {
    SingleBooleanFormFieldTableComponent
} from "@app/shared/form-controls/single-boolean-form-field-table/single-boolean-form-field-table.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.item.attributesWithEnumType" loopVariable="attributeWithEnumType" ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="attributeWithEnumType.enum.enumName.pascalCase" ]
        [ searchValue="appellatio-comis" replaceByExpression="attributeWithEnumType.enum.enumName.kebabCase" ]
}}}@  */
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
/* @tt{{{   @if [ conditionExpression="attributeWithEnumType.isList"] }}}@ */
import {AppellatioComisInputTableComponent} from "@app/enum/appellatio-comis-input-table/appellatio-comis-input-table.component";
/* @tt{{{   @else  }}}@ */
import {AppellatioComisSelectorComponent} from "@app/enum/appellatio-comis-input-selection/appellatio-comis-selector.component";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @end-foreach  }}}@ */


/* @tt{{{ 
    @foreach [ iteratorExpression="model.item.directlyNestedItems" loopVariable="nestedItem" ]
    @replace-value-by-expression
        [ searchValue="articulus-interior" replaceByExpression="nestedItem.itemName.kebabCase" ]
        [ searchValue="articulusInterior" replaceByExpression="nestedItem.itemName.camelCase" ]
        [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]

}}}@  */
import {
    OpusMagnumArticulusInteriorTableComponent
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table.component";
import {
    OpusMagnumArticulusInteriorFormPartComponent
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part.component";
import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.item.listReferencedItems" loopVariable="listReferencedItem" ]
    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="listReferencedItem.itemName.pascalCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="listReferencedItem.itemName.kebabCase" ]

}}}@  */
import {
    MembrumRelatumReferenceTableComponent
} from "@app/reference/membrum-relatum-reference-table/membrum-relatum-reference-table.component";
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.item.singleReferencedItems" loopVariable="singleReferencedItem" ]
    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="singleReferencedItem.itemName.pascalCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="singleReferencedItem.itemName.kebabCase" ]

}}}@  */
import {
    MembrumRelatumReferenceFieldComponent
} from "@app/reference/membrum-relatum-reference-field/membrum-relatum-reference-field.component";
/* @tt{{{   @end-foreach  }}}@ */

@Component({
    selector: 'app-opus-magnum-silva-optionum-form-part',
    templateUrl: './opus-magnum-silva-optionum-form-part.component.html',
    styleUrls: ['./opus-magnum-silva-optionum-form-part.component.scss'],
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatToolbarModule,
        MatTableModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatExpansionModule,
        MatSidenavModule,
        MatListModule,
        MatDialogModule,
        FieldWrapperComponent,
        TranslocoPipe,
        /*
        @tt{{{
            @if [ conditionExpression="model.tabs.isNotEmpty()" ]
            
        }}}@
         */
        MatTabGroup,
        MatTab,
        /*
        @tt{{{
            @end-if 
        }}}@
         */
        /*
        @tt{{{
            @if [ conditionExpression="model.containsNamedSectionSplitBlocks()" ]
            
        }}}@
         */
        SectionSplitterComponent,
        /*
        @tt{{{
            @end-if 
        }}}@
         */
        /*
        @tt{{{
            @if [ conditionExpression="model.containsTextBlocks()" ]
            
        }}}@
         */
        TextBlockComponent,
        /*
        @tt{{{
            @end-if 
        }}}@
         */
        /* @tt{{{ 
            @foreach [ iteratorExpression="model.item.attributesWithItemType" loopVariable="attributeWithItemType" ]
            @replace-value-by-expression
                [ searchValue="articulus-interior" replaceByExpression="attributeWithItemType.referencedItem.itemName.kebabCase" ]
                [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

        }}}@  */
        /* @tt{{{   @if [ conditionExpression="attributeWithItemType.isList"] }}}@ */
        OpusMagnumArticulusInteriorTableComponent,
        /* @tt{{{   @end-if }}}@ */
        OpusMagnumArticulusInteriorFormPartComponent,
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{   @if [ conditionExpression="model.item.containsTextAttributes"] }}}@ */
        TextInputComponent,
        /* @tt{{{   @end-if }}}@ */
        /* @tt{{{   @if [ conditionExpression="model.item.containsBooleanAttributes"] }}}@ */
        BooleanInputComponent,
        /* @tt{{{   @end-if }}}@ */
        /* @tt{{{   @if [ conditionExpression="model.item.containsNumberAttributes"] }}}@ */
        NumberInputComponent,
        /* @tt{{{   @end-if }}}@ */
        /* @tt{{{   @if [ conditionExpression="model.item.containsTextListAttributes"] }}}@ */
        SingleTextFormFieldTableComponent,
        /* @tt{{{   @end-if }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.listReferencedItems" loopVariable="listReferencedItem" ]
            @replace-value-by-expression
                [ searchValue="MembrumRelatum" replaceByExpression="listReferencedItem.itemName.pascalCase" ]
        }}}@ */
        MembrumRelatumReferenceTableComponent,
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.singleReferencedItems" loopVariable="singleReferencedItem" ]
            @replace-value-by-expression
                [ searchValue="MembrumRelatum" replaceByExpression="singleReferencedItem.itemName.pascalCase" ]
        }}}@ */
        MembrumRelatumReferenceFieldComponent,
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{
        @if [ conditionExpression="model.item.containsNumberListAttributes"]
        @print-text [ text="        SingleNumberFormFieldTableComponent,"]
        @end-if
        @if [ conditionExpression="model.item.containsBooleanListAttributes"]
        @print-text [ text="        SingleBooleanFormFieldTableComponent,"]
        @end-if
        }}}@ */
        /* @tt{{{   @ignore-text }}}@ */
        DatepickerInputComponent,
        /* @tt{{{   @end-ignore-text }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributesWithEnumType" loopVariable="attributeWithEnumType" ]
            @replace-value-by-expression
                [ searchValue="AppellatioComis" replaceByExpression="attributeWithEnumType.enum.enumName.pascalCase" ]
        }}}@ */
        /* @tt{{{   @if [ conditionExpression="attributeWithEnumType.isList"] }}}@ */
        AppellatioComisInputTableComponent,
        /* @tt{{{   @else }}}@ */
        AppellatioComisSelectorComponent,
        /* @tt{{{   @end-if }}}@ */
        /* @tt{{{   @end-foreach }}}@ */
    ]
})
export class OpusMagnumSilvaOptionumFormPartComponent implements OnInit {
    @Input({ required: true }) silvaOptionumForm!: FormGroup<OpusMagnumSilvaOptionumFormPartGroup>;

    /* @tt{{{
        @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { it.isList }" loopVariable="attributeWithItemType" ]
        @replace-value-by-expression
            [ searchValue="articulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
            [ searchValue="FormGroup<OpusMagnumArticulusInteriorFormPartGroup>" replaceByExpression="attributeWithItemType.angularFormControlType(model.entity.entityName)" ]

    }}}@  */
    readonly articulusInteriorIteratusEditState = new FormArrayEditState<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>(() => this.articulusInteriorIteratusControl);
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    readonly articulusInteriorOptionalisIteratusEditState = new FormArrayEditState<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>(() => this.articulusInteriorOptionalisIteratusControl);

    protected campusTextusObligatoriusControl!: FormControl<string>
    protected campusTextusObligatoriusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    /* @tt{{{   @end-ignore-text  }}}@ */

    /* @tt{{{
    @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

    @replace-value-by-expression
        [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
        [ searchValue="FormControl<string | null>" replaceByExpression="attribute.angularFormControlTypeWithCollection(model.entity.entityName)" ]

    }}}@  */
    /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
    protected campusTextusOptionalisIsNotNullControl!: FormControl<boolean>
    protected campusTextusOptionalisIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    /* @tt{{{   @end-if  }}}@ */
    protected campusTextusOptionalisControl!: FormControl<string | null>
    protected campusTextusOptionalisValidatorNames!: ReadonlyArray<ValidatorTranslation>
    /* @tt{{{  @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */

    protected articulusInteriorIteratusControl!: FormArray<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>
    protected articulusInteriorIteratusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorOptionalisIteratusControl!: FormArray<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>
    protected articulusInteriorOptionalisIteratusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorOptionalisIteratusIsNotNullControl!: FormControl<boolean>
    protected articulusInteriorOptionalisIteratusIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected appellatioOptionalisIteratusControl!: FormArray<FormControl<AppellatioComisEnum>>
    protected appellatioOptionalisIteratusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected appellatioOptionalisIteratusIsNotNullControl!: FormControl<boolean>
    protected appellatioOptionalisIteratusIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected campusDieiIsNotNullControl!: FormControl<boolean>
    protected campusDieiIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected campusDieiControl!: FormControl<Date | null>
    protected campusDieiValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected campusBivalensControl!: FormControl<boolean>
    protected campusBivalensValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected appellatioControl!: FormControl<AppellatioComisEnum>
    protected appellatioValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorSingularisControl!: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>
    protected articulusInteriorSingularisValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorSingularisOptionalisControl!: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>
    protected articulusInteriorSingularisOptionalisValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorSingularisOptionalisIsNotNullControl!: FormControl<boolean>
    protected articulusInteriorSingularisOptionalisIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected indexUnicusControl!: FormControl<UUID>
    protected indexUnicusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected campusNumerorumControl!: FormControl<number>
    protected campusNumerorumValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected iteratioSimpliciumTextuumControl!: FormArray<FormControl<string>>
    protected iteratioSimpliciumTextuumValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected relatioAdEntitatemOptionalisIteratusControl!: FormArray<FormControl<UUID>>
    protected relatioAdEntitatemOptionalisIteratusValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected relatioAdEntitatemOptionalisIteratusIsNotNullControl!: FormControl<boolean>
    protected relatioAdEntitatemOptionalisIteratusIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected relatioAdEntitatemOptionalisControl!: FormControl<UUID | null>
    protected relatioAdEntitatemOptionalisValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected relatioAdEntitatemOptionalisIsNotNullControl!: FormControl<boolean>
    protected relatioAdEntitatemOptionalisIsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
    /* @tt{{{   @end-ignore-text  }}}@ */

    constructor(private readonly silvaOptionumFormValidationService: OpusMagnumSilvaOptionumFormPartValidationService,) {
    }

    ngOnInit() {
        /* @tt{{{ @ignore-text  }}}@ */
        this.campusTextusObligatoriusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius]
        this.campusTextusObligatoriusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius)
        /* @tt{{{   @end-ignore-text }}}@ */
        /* @tt{{{ 
        @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]

        }}}@  */
        /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
        this.campusTextusOptionalisIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull]
        this.campusTextusOptionalisIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull)
        /* @tt{{{   @end-if  }}}@ */
        this.campusTextusOptionalisControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis]
        this.campusTextusOptionalisValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis)
        /* @tt{{{  @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        this.articulusInteriorIteratusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus]
        this.articulusInteriorIteratusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus)
        this.articulusInteriorOptionalisIteratusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus]
        this.articulusInteriorOptionalisIteratusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus)
        this.articulusInteriorOptionalisIteratusIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull]
        this.articulusInteriorOptionalisIteratusIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull)
        this.appellatioOptionalisIteratusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus]
        this.appellatioOptionalisIteratusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus)
        this.appellatioOptionalisIteratusIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull]
        this.appellatioOptionalisIteratusIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull)
        this.campusDieiIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull]
        this.campusDieiIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull)
        this.campusDieiControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDiei]
        this.campusDieiValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusDiei)
        this.campusBivalensControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens]
        this.campusBivalensValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens)
        this.appellatioControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatio]
        this.appellatioValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.appellatio)
        this.articulusInteriorSingularisControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis]
        this.articulusInteriorSingularisValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis)
        this.indexUnicusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus]
        this.indexUnicusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus)
        this.campusNumerorumControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum]
        this.campusNumerorumValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum)
        this.articulusInteriorSingularisOptionalisControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis]
        this.articulusInteriorSingularisOptionalisValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis)
        this.articulusInteriorSingularisOptionalisIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull]
        this.articulusInteriorSingularisOptionalisIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull)
        this.iteratioSimpliciumTextuumControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum]
        this.iteratioSimpliciumTextuumValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum)
        this.relatioAdEntitatemOptionalisIteratusControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus]
        this.relatioAdEntitatemOptionalisIteratusValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus)
        this.relatioAdEntitatemOptionalisIteratusIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull]
        this.relatioAdEntitatemOptionalisIteratusIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull)
        this.relatioAdEntitatemOptionalisControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis]
        this.relatioAdEntitatemOptionalisValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis)
        this.relatioAdEntitatemOptionalisIsNotNullControl = this.silvaOptionumForm.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull]
        this.relatioAdEntitatemOptionalisIsNotNullValidatorNames = this.silvaOptionumFormValidationService.validatorNames(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull)
        /* @tt{{{   @end-ignore-text  }}}@ */
    }
}
