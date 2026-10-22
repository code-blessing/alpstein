import {ViewItemLabelService} from '@app/view-tabs/view-item-label.service';
import {ViewDescriptor} from '@app/view-tabs/view-descriptor';

const COUNTRY_EDIT: ViewDescriptor = {entityName: 'country', viewKind: 'edit', itemId: '5'};

describe('ViewItemLabelService', () => {
    let viewItemLabels: ViewItemLabelService;

    beforeEach(() => {
        viewItemLabels = new ViewItemLabelService();
    });

    it('should report the label of the edited item', () => {
        viewItemLabels.setLabel('country', '5', 'Switzerland');

        expect(viewItemLabels.label(COUNTRY_EDIT)).toBe('Switzerland');
    });

    it('should not know the label of another item, entity or view kind', () => {
        viewItemLabels.setLabel('country', '5', 'Switzerland');

        expect(viewItemLabels.label({...COUNTRY_EDIT, itemId: '6'})).toBeUndefined();
        expect(viewItemLabels.label({...COUNTRY_EDIT, entityName: 'city'})).toBeUndefined();
        expect(viewItemLabels.label({entityName: 'country', viewKind: 'board'})).toBeUndefined();
    });

    it('should announce every label it takes', () => {
        let announcements = 0;
        viewItemLabels.labelsChanged$.subscribe(() => announcements++);

        viewItemLabels.setLabel('country', '5', 'Switzerland');
        viewItemLabels.setLabel('country', '5', 'Swaziland');

        expect(announcements).toBe(2);
        expect(viewItemLabels.label(COUNTRY_EDIT)).toBe('Swaziland');
    });
});
