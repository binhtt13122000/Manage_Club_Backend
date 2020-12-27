package binhtt.dev.manage.services.imp;

import binhtt.dev.manage.entities.Event;
import binhtt.dev.manage.repositories.EventRepository;
import binhtt.dev.manage.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Override
    public List<Event> getEvents(String name, boolean isInternal) {
        return eventRepository.getEventsByEventNameContainingAndInternal(name, isInternal);
    }
}
