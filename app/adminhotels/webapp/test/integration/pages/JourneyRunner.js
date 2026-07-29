sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/admin/adminhotels/test/integration/pages/HotelsList.gen",
	"hms/admin/adminhotels/test/integration/pages/HotelsObjectPage.gen"
], function (JourneyRunner, HotelsListGenerated, HotelsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/admin/adminhotels') + '/test/flpSandbox.html#hmsadminadminhotels-tile',
        pages: {
			onTheHotelsListGenerated: HotelsListGenerated,
			onTheHotelsObjectPageGenerated: HotelsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

