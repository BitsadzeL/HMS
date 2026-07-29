sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/admin/adminguests/test/integration/pages/GuestsList.gen",
	"hms/admin/adminguests/test/integration/pages/GuestsObjectPage.gen"
], function (JourneyRunner, GuestsListGenerated, GuestsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/admin/adminguests') + '/test/flpSandbox.html#hmsadminadminguests-tile',
        pages: {
			onTheGuestsListGenerated: GuestsListGenerated,
			onTheGuestsObjectPageGenerated: GuestsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

