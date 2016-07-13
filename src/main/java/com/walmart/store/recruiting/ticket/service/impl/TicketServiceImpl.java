package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.Seat;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * Flow: I am using the ids of seats to hold the seats, if there are seats that are together, the Venue method will return the ids of the seats.
	 * If there are not ids, this means we did not find any seats together, so we drop the holding for now.
	 * If there are ids, we will create a seat hold with the ids, and mark the seats in the venues as not available. 
	 */
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
			else
			{
				/**
				 * We did not get the seats in the bulk, lets try a recursive method of finding other seats by gruops
				 */
				
				ArrayList<Integer> groupSeats=new ArrayList<>();
				int procuredSeats=0;
				int groupsize=numSeats-1;
				while(procuredSeats<=numSeats&&groupsize>0)
				{
					int[] tmpseats = venue.getConsecutiveSeatsIds(groupsize);
					if(tmpseats.length>0)
					{
						procuredSeats+=tmpseats.length;
						for(int y:tmpseats)
						{
							groupSeats.add(y);
						}
					}
					else
					{
						groupsize--;
					}

					
				}
				
				if(groupSeats.size()==numSeats)
				{
					SeatHold seatHold = new SeatHold(holdId, numSeats,seatids);
					optionalSeatHold = Optional.of(seatHold);
					seatHoldMap.put(holdId, seatHold);
					seatsAvailable -= numSeats;
					int [] tmp=groupSeats.stream().mapToInt(it->it).toArray();
					venue.markSeatUnavailable(tmp);
				}
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
