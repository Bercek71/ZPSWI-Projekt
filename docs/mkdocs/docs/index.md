# Hotel Reservation System

[![Java CI tests](https://github.com/Bercek71/ZPSWI-Projekt/actions/workflows/gradle.yml/badge.svg)](https://github.com/Bercek71/ZPSWI-Projekt/actions/workflows/gradle.yml)


Welcome to the **Hotel Reservation System** documentation. This system enables users to browse, book, and manage hotel reservations through an intuitive and efficient web interface. It also provides an API for integrating with other systems.

## Overview

The Hotel Reservation System offers the following key features:

- **Room Booking**: Users can search for available rooms based on dates and preferences, and make a reservation.
- **Customer Management**: Hotels can manage customer information, including personal details and booking history.
- **Payment Processing**: Secure payment processing is integrated to handle transactions for reservations.
- **Admin Panel**: Administrators can view bookings, manage rooms, and monitor overall system performance.
- **REST API**: Expose endpoints to manage rooms, bookings, and customers programmatically.

## Table of Contents

- [Data Model](data_model.md): A detailed explanation of the system’s data structure, including rooms, customers, bookings, and payments.
- [API Documentation](api.md): Documentation of the available API endpoints for managing reservations, rooms, and customers.
- [Setup Instructions](setup.md): Step-by-step guide to installing, configuring, and running the system.

## System Architecture

The system is built using the following technologies:

- **Backend**: Java Quarkus for the REST API with PostgreSQL as the database.
- **Frontend**: React for user interaction and managing reservations.
- **Database**: PostgreSQL stores data related to rooms, bookings, payments, and customers.
- **Payment Integration**: Integration with a third-party service for secure payment processing.

## Key Entities

1. **Room**: Represents hotel rooms available for reservation.
2. **Customer**: Represents users of the system, who can make and manage bookings.
3. **Booking**: Represents a reservation made by a customer for a specific room.
4. **Payment**: Represents a payment made by the customer for their booking.

For more details on the data structure, see the [Data Model](data_model.md) section.

## Next Steps

- Learn about the [Data Model](data_model.md) to understand the system's structure.
- Explore the [API Documentation](api.md) for integrating the Hotel Reservation System with other applications.