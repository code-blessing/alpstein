import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-section-splitter',
    templateUrl: './section-splitter.component.html',
    styleUrls: ['./section-splitter.component.scss'],
})
export class SectionSplitterComponent {
    @Input({required: true}) label!: String;

}
