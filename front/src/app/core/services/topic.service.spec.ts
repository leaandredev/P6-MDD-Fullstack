import { TestBed } from '@angular/core/testing';

import { TopicService as TopicService } from './topic.service';

describe('TopicsService', () => {
  let service: TopicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TopicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
