sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/manageguests/manageguests/test/integration/pages/GuestsList.gen",
	"hms/manageguests/manageguests/test/integration/pages/GuestsObjectPage.gen"
], function (JourneyRunner, GuestsListGenerated, GuestsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/manageguests/manageguests') + '/test/flpSandbox.html#hmsmanageguestsmanageguests-tile',
        pages: {
			onTheGuestsListGenerated: GuestsListGenerated,
			onTheGuestsObjectPageGenerated: GuestsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

