package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.Seat;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A ticket service implementation.
 */
public class TicketServiceImpl implements TicketService {

	private int seatsAvailable;
	private int seatsReserved;
	private Map<String, SeatHold> seatHoldMap = new HashMap<>();

	private Map<Integer, Seat> seats;

	private Venue venue;

	public TicketServiceImpl(Venue venue) {
		seatsAvailable = venue.getMaxSeats();
		seats = venue.getSeatArrangements();
		this.venue = venue;
	}

	@Override
	public int numSeatsAvailable() {
		return seatsAvailable;
	}

	public int numSeatsReserved() {
		return this.seatsReserved;
	}

	@Override
	public Optional<SeatHold> findAndHoldSeats(int numSeats) {
		Optional<SeatHold> optionalSeatHold = Optional.empty();

		if (seatsAvailable >= numSeats) {
			String holdId = generateId();
			int[] seatids = venue.getConsecutiveSeatsIds(numSeats);
			if (seatids.length > 0) {
				
				SeatHold seatHold = new SeatHold(holdId, numSeats,seatids);
				optionalSeatHold = Optional.of(seatHold);
				seatHoldMap.put(holdId, seatHold);
				seatsAvailable -= numSeats;
				venue.markSeatUnavailable(seatids);
			}

		}

		return optionalSeatHold;
	}

	@Override
	public Optional<String> reserveSeats(String seatHoldId) {
		Optional<String> optionalReservation = Optional.empty();
		;
		SeatHold seatHold = seatHoldMap.get(seatHoldId);
		if (seatHold != null) {
			int [] reservedSeatIds=seatHold.getSeats();
			if(reservedSeatIds.length>0)
			{
				seatsReserved += seatHold.getNumSeats();
				optionalReservation = Optional.of(seatHold.getId());
				seatHoldMap.remove(seatHoldId);
				venue.markSeatReserved(reservedSeatIds);
			}
			
		}

		return optionalReservation;
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}

}
