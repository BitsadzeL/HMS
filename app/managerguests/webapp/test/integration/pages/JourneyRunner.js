sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/manager/managerguests/test/integration/pages/GuestsList.gen",
	"hms/manager/managerguests/test/integration/pages/GuestsObjectPage.gen"
], function (JourneyRunner, GuestsListGenerated, GuestsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/manager/managerguests') + '/test/flpSandbox.html#hmsmanagermanagerguests-tile',
        pages: {
			onTheGuestsListGenerated: GuestsListGenerated,
			onTheGuestsObjectPageGenerated: GuestsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

