import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {UUID} from "@app/shared/uuid";
import {MembrumRelatumSearchCriteriaWTO} from "@app/wto/membrum-relatum-search-criteria.wto";
import {MembrumRelatumSearchResultWTO} from "@app/wto/membrum-relatum-search-result.wto";
import {MembrumRelatumByIdsCriteriaWTO} from "@app/wto/membrum-relatum-by-ids-criteria.wto";
import {MembrumRelatumByIdsResultWTO} from "@app/wto/membrum-relatum-by-ids-result.wto";


@Injectable({providedIn: 'root'})
export class MembrumRelatumService {
    private readonly baseUrl = 'http://localhost:8081/api/membrum-relatum';

    constructor(private readonly http: HttpClient) {}

    getMembrumRelatumList(): Observable<MembrumRelatumWTO[]> {
        return this.http.get<MembrumRelatumWTO[]>(this.baseUrl);
    }

    searchMembrumRelatumList(searchCriteria: MembrumRelatumSearchCriteriaWTO): Observable<MembrumRelatumSearchResultWTO> {
        return this.http.post<MembrumRelatumSearchResultWTO>(`${this.baseUrl}/search`, searchCriteria);
    }

    getMembrumRelatumListByIds(criteria: MembrumRelatumByIdsCriteriaWTO): Observable<MembrumRelatumByIdsResultWTO> {
        return this.http.post<MembrumRelatumByIdsResultWTO>(`${this.baseUrl}/by-ids`, criteria);
    }

    getMembrumRelatumById(clavisPrimaria: UUID): Observable<MembrumRelatumWTO | null> {
        return this.http.get<MembrumRelatumWTO | null>(`${this.baseUrl}/${clavisPrimaria}`);
    }

    deleteMembrumRelatum(clavisPrimaria: UUID): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${clavisPrimaria}`);
    }

    updateMembrumRelatum(membrumRelatum: MembrumRelatumWTO): Observable<MembrumRelatumWTO> {
        return this.http.put<MembrumRelatumWTO>(`${this.baseUrl}/${membrumRelatum.clavisPrimaria}`, membrumRelatum);
    }

    createMembrumRelatum(membrumRelatum: MembrumRelatumWTO): Observable<MembrumRelatumWTO> {
        return this.http.post<MembrumRelatumWTO>(this.baseUrl, membrumRelatum);
    }
}
