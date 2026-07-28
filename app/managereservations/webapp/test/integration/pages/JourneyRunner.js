sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/managereservations/managereservations/test/integration/pages/ReservationsList.gen",
	"hms/managereservations/managereservations/test/integration/pages/ReservationsObjectPage.gen"
], function (JourneyRunner, ReservationsListGenerated, ReservationsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/managereservations/managereservations') + '/test/flpSandbox.html#hmsmanagereservationsmanageres-tile',
        pages: {
			onTheReservationsListGenerated: ReservationsListGenerated,
			onTheReservationsObjectPageGenerated: ReservationsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

