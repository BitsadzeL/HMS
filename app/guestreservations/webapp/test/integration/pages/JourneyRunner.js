sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/guest/guestreservations/test/integration/pages/ReservationsList.gen",
	"hms/guest/guestreservations/test/integration/pages/ReservationsObjectPage.gen"
], function (JourneyRunner, ReservationsListGenerated, ReservationsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/guest/guestreservations') + '/test/flpSandbox.html#hmsguestguestreservations-tile',
        pages: {
			onTheReservationsListGenerated: ReservationsListGenerated,
			onTheReservationsObjectPageGenerated: ReservationsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

