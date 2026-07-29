sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/admin/adminmanagers/test/integration/pages/ManagersList.gen",
	"hms/admin/adminmanagers/test/integration/pages/ManagersObjectPage.gen"
], function (JourneyRunner, ManagersListGenerated, ManagersObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/admin/adminmanagers') + '/test/flpSandbox.html#hmsadminadminmanagers-tile',
        pages: {
			onTheManagersListGenerated: ManagersListGenerated,
			onTheManagersObjectPageGenerated: ManagersObjectPageGenerated
        },
        async: true
    });

    return runner;
});

