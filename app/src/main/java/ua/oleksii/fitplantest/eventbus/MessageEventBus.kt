package ua.oleksii.fitplantest.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ua.oleksii.fitplantest.eventbus.eventmodels.EventModel

enum class MessageEventBus {
    INSTANCE;

    private val bus =
        PublishSubject.create<EventModel>() // the actual publisher handling all of the events

    fun send(event: EventModel) {
        bus.onNext(event) // the message being sent to all subscribers
    }

    fun toObservable(): Observable<EventModel> {
        return bus // return the publisher itself as an observable to subscribe to
    }

}