/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemRepositoryRenderer"
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
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.kotlinAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.service.silvaoptionum

import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumByIdsCriteriaBO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumSearchCriteriaBO
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */

/**
 * Port for persisting the SilvaOptionum root object [SilvaOptionumBO]. The implementation
 * lives in the persistence module, so the service (business) layer stays independent of
 * any persistence technology.
 *
 * Always operates on the whole [SilvaOptionumBO] aggregate, never on nested items.
 */
interface SilvaOptionumRepository {

    fun findAll(): List<SilvaOptionumBO>

    fun findById(indexUnicus: UUID): SilvaOptionumBO?

    fun findByIds(criteria: SilvaOptionumByIdsCriteriaBO): List<SilvaOptionumBO>

    fun search(searchCriteria: SilvaOptionumSearchCriteriaBO): List<SilvaOptionumBO>

    /**
     * Stores a SilvaOptionum that does not exist yet and returns it under the primary key the
     * persistence handed out for it. Handing out identity is the job of the persistence, like a
     * sequence of a database, so the key the given aggregate carries is ignored.
     */
    fun create(silvaOptionum: SilvaOptionumBO): SilvaOptionumBO

    fun update(silvaOptionum: SilvaOptionumBO): SilvaOptionumBO

    fun deleteById(indexUnicus: UUID)
}
