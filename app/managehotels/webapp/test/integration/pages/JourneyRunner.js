sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/managehotels/managehotels/test/integration/pages/HotelsList.gen",
	"hms/managehotels/managehotels/test/integration/pages/HotelsObjectPage.gen"
], function (JourneyRunner, HotelsListGenerated, HotelsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/managehotels/managehotels') + '/test/flpSandbox.html#hmsmanagehotelsmanagehotels-tile',
        pages: {
			onTheHotelsListGenerated: HotelsListGenerated,
			onTheHotelsObjectPageGenerated: HotelsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

