/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemServiceRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `silva-optionum.service.ts`
 * - path: `service/silva-optionum.service.ts`
 */
object ItemServiceRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |
          |import {Injectable} from '@angular/core';
          |import {HttpClient} from '@angular/common/http';
          |import {Observable} from 'rxjs';
          |import {${model.itemName.pascalCase}WTO} from "@app/wto/${model.itemName.kebabCase}.wto";
          |import {${model.itemName.pascalCase}SearchCriteriaWTO} from "@app/wto/${model.itemName.kebabCase}-search-criteria.wto";
          |import {${model.itemName.pascalCase}SearchResultWTO} from "@app/wto/${model.itemName.kebabCase}-search-result.wto";
          |import {${model.itemName.pascalCase}ByIdsCriteriaWTO} from "@app/wto/${model.itemName.kebabCase}-by-ids-criteria.wto";
          |import {${model.itemName.pascalCase}ByIdsResultWTO} from "@app/wto/${model.itemName.kebabCase}-by-ids-result.wto";
          |${ if(model.hasUuidPrimaryKey) { """import {${model.primaryKeyAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |""" } else { """""" } }
          |
          |@Injectable({providedIn: 'root'})
          |export class ${model.itemName.pascalCase}Service {
          |    private readonly baseUrl = 'http://localhost:8081/api/${model.itemName.kebabCase}';
          |
          |    constructor(private readonly http: HttpClient) {}
          |
          |    get${model.itemName.pascalCase}List(): Observable<${model.itemName.pascalCase}WTO[]> {
          |        return this.http.get<${model.itemName.pascalCase}WTO[]>(this.baseUrl);
          |    }
          |
          |    search${model.itemName.pascalCase}List(searchCriteria: ${model.itemName.pascalCase}SearchCriteriaWTO): Observable<${model.itemName.pascalCase}SearchResultWTO> {
          |        return this.http.post<${model.itemName.pascalCase}SearchResultWTO>(`${"$"}{this.baseUrl}/search`, searchCriteria);
          |    }
          |
          |    /**
          |     * Resolves a whole set of references to this item in one call, so that a list of stored
          |     * identifiers can be shown by the display attributes instead of the bare primary keys.
          |     */
          |    get${model.itemName.pascalCase}ListByIds(criteria: ${model.itemName.pascalCase}ByIdsCriteriaWTO): Observable<${model.itemName.pascalCase}ByIdsResultWTO> {
          |        return this.http.post<${model.itemName.pascalCase}ByIdsResultWTO>(`${"$"}{this.baseUrl}/by-ids`, criteria);
          |    }
          |
          |    get${model.itemName.pascalCase}ById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.typescriptAttributeType}): Observable<${model.itemName.pascalCase}WTO | null> {
          |        return this.http.get<${model.itemName.pascalCase}WTO | null>(`${"$"}{this.baseUrl}/${"$"}{${model.primaryKeyAttribute.attributeName.camelCase}}`);
          |    }
          |
          |    delete${model.itemName.pascalCase}(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.typescriptAttributeType}): Observable<void> {
          |        return this.http.delete<void>(`${"$"}{this.baseUrl}/${"$"}{${model.primaryKeyAttribute.attributeName.camelCase}}`);
          |    }
          |
          |    update${model.itemName.pascalCase}(${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO): Observable<${model.itemName.pascalCase}WTO> {
          |        return this.http.put<${model.itemName.pascalCase}WTO>(`${"$"}{this.baseUrl}/${"$"}{${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}}`, ${model.itemName.camelCase});
          |    }
          |
          |    create${model.itemName.pascalCase}(${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO): Observable<${model.itemName.pascalCase}WTO> {
          |        return this.http.post<${model.itemName.pascalCase}WTO>(this.baseUrl, ${model.itemName.camelCase});
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "service/${model.itemName.kebabCase}.service.ts"
    }
}