package binhtt.dev.manage.services;

import binhtt.dev.manage.entities.Event;

import java.util.List;

public interface EventService {
    List<Event> getEvents(String name, boolean isInternal);
}
