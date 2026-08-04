sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/manager/managerhotel/test/integration/pages/HotelsList.gen",
	"hms/manager/managerhotel/test/integration/pages/HotelsObjectPage.gen"
], function (JourneyRunner, HotelsListGenerated, HotelsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/manager/managerhotel') + '/test/flpSandbox.html#hmsmanagermanagerhotel-tile',
        pages: {
			onTheHotelsListGenerated: HotelsListGenerated,
			onTheHotelsObjectPageGenerated: HotelsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

