/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemExampleDataCreatorRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.exampledata.bo

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO
/* @tt{{{
    @foreach [ iteratorExpression="model.exampleDataGeneratorConfigs" loopVariable="exampleDataGeneratorConfig" ]
    @replace-value-by-expression
        [ searchValue="RandomString" replaceByExpression="exampleDataGeneratorConfig.generatorNamePrefix.pascalCase" ]
        [ searchValue="randomString" replaceByExpression="exampleDataGeneratorConfig.generatorNamePrefix.camelCase" ]
}}}@ */
import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.RandomStringDataGenerator
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.referencedItems" loopVariable="referencedItem" ]
    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
        [ searchValue="membrumrelatum" replaceByExpression="referencedItem.itemName.lowerCase" ]
}}}@ */
import org.codeblessing.alpstein.server.exampledata.membrumrelatum.MembrumRelatumExampleDataFetcher
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{   @ignore-text  }}}@ */
import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.RandomBooleanDataGenerator
import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.RandomNumberDataGenerator
import java.util.UUID
/* @tt{{{   @end-ignore-text  }}}@ */



/**
 * Creates example data for the business object [SilvaOptionumBO].
 *
 * Delegates the creation of nested objects to the dedicated example data creators of the
 * respective business objects.
 */
@Component
class SilvaOptionumExampleDataCreator(
    /* @tt{{{
        @foreach [ iteratorExpression="model.directlyNestedItems" loopVariable="nestedItem" ]
        @replace-value-by-expression
            [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]
            [ searchValue="articulusInterior" replaceByExpression="nestedItem.itemName.camelCase" ]
    }}}@ */
    private val articulusInteriorExampleDataCreator: ArticulusInteriorExampleDataCreator,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{
        @foreach [ iteratorExpression="model.usedEnums" loopVariable="usedEnum" ]
        @replace-value-by-expression
            [ searchValue="AppellatioComis" replaceByExpression="usedEnum.enumName.pascalCase" ]
            [ searchValue="appellatioComis" replaceByExpression="usedEnum.enumName.camelCase" ]
    }}}@ */
    private val appellatioComisExampleDataCreator: AppellatioComisExampleDataCreator,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{
        @foreach [ iteratorExpression="model.exampleDataGeneratorConfigs" loopVariable="exampleDataGeneratorConfig" ]
        @replace-value-by-expression
            [ searchValue="RandomString" replaceByExpression="exampleDataGeneratorConfig.generatorNamePrefix.pascalCase" ]
            [ searchValue="randomString" replaceByExpression="exampleDataGeneratorConfig.generatorNamePrefix.camelCase" ]
    }}}@ */
    private val randomStringDataGenerator: RandomStringDataGenerator,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{
        @foreach [ iteratorExpression="model.referencedItems" loopVariable="referencedItem" ]
        @replace-value-by-expression
            [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
            [ searchValue="membrumRelatum" replaceByExpression="referencedItem.itemName.camelCase" ]
            [ searchValue="membrumrelatum" replaceByExpression="referencedItem.itemName.lowerCase" ]
    }}}@ */
    private val membrumRelatumExampleDataFetcher: MembrumRelatumExampleDataFetcher,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    private val randomNumberDataGenerator: RandomNumberDataGenerator,
    private val randomBooleanDataGenerator: RandomBooleanDataGenerator,
    /* @tt{{{   @end-ignore-text  }}}@ */
) {

    fun create(dataContext: DataContext): SilvaOptionumBO = SilvaOptionumBO(
        /* @tt{{{
            @foreach [ iteratorExpression="model.builtInAttributes.filter { !it.isItemReference }" loopVariable="builtInAttribute" ]
            @replace-value-by-expression
                [ searchValue="iteratioSimpliciumTextuum" replaceByExpression="builtInAttribute.attributeName.camelCase" ]
                [ searchValue="campusTextusObligatorius" replaceByExpression="builtInAttribute.attributeName.camelCase" ]
                [ searchValue="randomString" replaceByExpression="builtInAttribute.exampleDataGeneratorConfig.generatorNamePrefix.camelCase" ]
        }}}@ */
        /* @tt{{{   @if [ conditionExpression="builtInAttribute.isList"]  }}}@ */
        iteratioSimpliciumTextuum = randomStringDataGenerator.generateDataList(dataContext, size = FakerHelper.innerListRandomSize(dataContext)),
        /* @tt{{{   @else }}}@ */
        campusTextusObligatorius = randomStringDataGenerator.generateData(dataContext),
        /* @tt{{{   @end-if  }}}@ */
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.attributesWithItemType" loopVariable="itemAttribute" ]
            @replace-value-by-expression
                [ searchValue="articulusInteriorSingularis" replaceByExpression="itemAttribute.attributeName.camelCase" ]
                [ searchValue="articulusInteriorIteratus" replaceByExpression="itemAttribute.attributeName.camelCase" ]
                [ searchValue="articulusInterior" replaceByExpression="itemAttribute.referencedItem.itemName.camelCase" ]
        }}}@ */
        /* @tt{{{   @if [ conditionExpression="itemAttribute.isList"]  }}}@ */
        articulusInteriorIteratus = articulusInteriorExampleDataCreator.createList(dataContext, FakerHelper.innerListRandomSize(dataContext)),
        /* @tt{{{   @else }}}@ */
        articulusInteriorSingularis = articulusInteriorExampleDataCreator.create(dataContext),
        /* @tt{{{   @end-if  }}}@ */

        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.attributesWithEnumType" loopVariable="enumAttribute" ]
            @replace-value-by-expression
                [ searchValue="appellatioOptionalisIteratus" replaceByExpression="enumAttribute.attributeName.camelCase" ]
                [ searchValue="appellatioComis" replaceByExpression="enumAttribute.enum.enumName.camelCase" ]
                [ searchValue="appellatio" replaceByExpression="enumAttribute.attributeName.camelCase" ]
        }}}@ */
        /* @tt{{{   @if [ conditionExpression="enumAttribute.isList"]  }}}@ */
        appellatioOptionalisIteratus = appellatioComisExampleDataCreator.createList(dataContext, FakerHelper.innerListRandomSize(dataContext)),
        /* @tt{{{   @else }}}@ */
        appellatio = appellatioComisExampleDataCreator.create(dataContext),
        /* @tt{{{   @end-if  }}}@ */
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.attributesWithItemReference" loopVariable="referenceAttribute" ]
            @replace-value-by-expression
                [ searchValue="relatioAdEntitatemOptionalisIteratus" replaceByExpression="referenceAttribute.attributeName.camelCase" ]
                [ searchValue="relatioAdEntitatemOptionalis" replaceByExpression="referenceAttribute.attributeName.camelCase" ]
                [ searchValue="membrumRelatum" replaceByExpression="referenceAttribute.referencedItem.itemName.camelCase" ]
        }}}@ */
        /* @tt{{{   @if [ conditionExpression="referenceAttribute.isList"]  }}}@ */
        relatioAdEntitatemOptionalisIteratus = membrumRelatumExampleDataFetcher.fetchRandomKeysList(dataContext),
        /* @tt{{{   @else }}}@ */
        relatioAdEntitatemOptionalis = membrumRelatumExampleDataFetcher.fetchRandomKey(dataContext),
        /* @tt{{{   @end-if  }}}@ */
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        indexUnicus = UUID.randomUUID(),
        campusNumerorum = randomNumberDataGenerator.generateData(dataContext),
        campusTextusOptionalis = randomStringDataGenerator.generateData(dataContext),
        articulusInteriorSingularisOptionalis = articulusInteriorExampleDataCreator.create(dataContext),
        articulusInteriorOptionalisIteratus = articulusInteriorExampleDataCreator.createList(dataContext, FakerHelper.innerListRandomSize(dataContext)),
        campusDiei = null,
        campusBivalens = randomBooleanDataGenerator.generateData(dataContext),
        /* @tt{{{   @end-ignore-text  }}}@ */
    )

    fun createList(dataContext: DataContext, size: Int): List<SilvaOptionumBO> =
        List( size = size) { create(dataContext) }
}
