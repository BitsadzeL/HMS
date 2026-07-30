using { GuestService }   from './guest-service';

annotate GuestService   with @requires: 'guest_access';