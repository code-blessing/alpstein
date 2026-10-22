import {convertToParamMap, ParamMap, Params, Router} from '@angular/router';
import {OpenViewTab, ViewTabsService} from '@app/view-tabs/view-tabs.service';
import {ViewDescriptor} from '@app/view-tabs/view-descriptor';

const FILM_BOARD: ViewDescriptor = {entityName: 'film', viewKind: 'board'};
const CITY_CREATE: ViewDescriptor = {entityName: 'city', viewKind: 'create'};
const COUNTRY_EDIT: ViewDescriptor = {entityName: 'country', viewKind: 'edit', itemId: '5'};

describe('ViewTabsService', () => {
    let currentQueryParams: Params;
    let viewTabs: ViewTabsService;

    beforeEach(() => {
        currentQueryParams = {};
        const router = {
            routerState: {snapshot: {root: {get queryParamMap(): ParamMap {
                return convertToParamMap(currentQueryParams);
            }}}},
        } as Router;
        viewTabs = new ViewTabsService(router);
    });

    function openTabs(queryParams: Params): ReadonlyArray<OpenViewTab> {
        return viewTabs.openTabs(convertToParamMap(queryParams));
    }

    it('should read a single tab and a whole set of tabs', () => {
        expect(openTabs({tab: '1:film.board'})).toEqual([{key: 1, view: FILM_BOARD}]);
        expect(openTabs({tab: ['1:film.board', '2:country.edit.5']}))
            .toEqual([{key: 1, view: FILM_BOARD}, {key: 2, view: COUNTRY_EDIT}]);
    });

    it('should drop a tab that is not readable or that repeats a key', () => {
        expect(openTabs({tab: ['1:film.board', 'film.board', 'x:city.create', '1:city.create']}))
            .toEqual([{key: 1, view: FILM_BOARD}]);
    });

    it('should fall back to the first tab when the active key is unknown', () => {
        const tabs = openTabs({tab: ['1:film.board', '2:city.create']});

        expect(viewTabs.activeTabKey(convertToParamMap({active: '2'}), tabs)).toBe(2);
        expect(viewTabs.activeTabKey(convertToParamMap({active: '9'}), tabs)).toBe(1);
        expect(viewTabs.activeTabKey(convertToParamMap({}), tabs)).toBe(1);
        expect(viewTabs.activeTabKey(convertToParamMap({active: '1'}), [])).toBeNull();
    });

    it('should append a new tab with a free key and activate it', () => {
        currentQueryParams = {tab: ['1:film.board', '4:city.create'], active: '1'};

        expect(viewTabs.openViewParams(COUNTRY_EDIT, 'newTab'))
            .toEqual({tab: ['1:film.board', '4:city.create', '5:country.edit.5'], active: 5});
    });

    it('should open a second tab of a view that is already open', () => {
        currentQueryParams = {tab: '1:film.board', active: '1'};

        expect(viewTabs.openViewParams(FILM_BOARD, 'newTab'))
            .toEqual({tab: ['1:film.board', '2:film.board'], active: 2});
    });

    it('should replace the view of the active tab and keep its key', () => {
        currentQueryParams = {tab: ['1:film.board', '2:city.create'], active: '1'};

        expect(viewTabs.openViewParams(COUNTRY_EDIT, 'currentTab'))
            .toEqual({tab: ['1:country.edit.5', '2:city.create'], active: 1});
    });

    it('should open a new tab in the current tab mode when no tab is open', () => {
        expect(viewTabs.openViewParams(CITY_CREATE, 'currentTab'))
            .toEqual({tab: ['1:city.create'], active: 1});
    });

    it('should activate the neighbour of the closed tab', () => {
        const tabs = openTabs({tab: ['1:film.board', '2:city.create', '3:country.edit.5']});

        expect(viewTabs.closeTabParams(tabs, 2, 2))
            .toEqual({tab: ['1:film.board', '3:country.edit.5'], active: 3});
        expect(viewTabs.closeTabParams(tabs, 3, 3))
            .toEqual({tab: ['1:film.board', '2:city.create'], active: 2});
        expect(viewTabs.closeTabParams(tabs, 1, 3))
            .toEqual({tab: ['1:film.board', '2:city.create'], active: 1});
        expect(viewTabs.closeTabParams(tabs.slice(0, 1), 1, 1))
            .toEqual({tab: [], active: null});
    });
});
