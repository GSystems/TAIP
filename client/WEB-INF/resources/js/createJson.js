
function findNextHotspot(data,id)
{
	for(let i = 0 ; i<data.features.length; i++) {
		if(data.features[i].properties.hotspotId == id)
			return data.features[i];
	}

	return null;
}

function getHotspotstsByTime(data,timestamp,type)
{

	let hotspots = new Object();
	hotspots.type = "FeatureCollection";
	hotspots.features = [];
	let key = timestamp.Year + "-" + timestamp.Month + "-" + timestamp.Day;

	if(type == "migrations")
	{
		for(let i = 0 ; i<data.features.length; i++) {
			var hotspot = data.features[i];
			if(hotspot.properties.fromHotspot.observationDate.year == timestamp.Year &&
		   		hotspot.properties.fromHotspot.observationDate.monthValue == timestamp.Month &&
		   		hotspot.properties.fromHotspot.observationDate.dayOfMonth == timestamp.Day
			) {
				hotspots.features.push(hotspot);
			}
		}
	} else {
		for(let i = 0 ; i<data.features.length; i++) {
			var hotspot = data.features[i];
			if(hotspot.properties.observationDate.year == timestamp.Year &&
		   		hotspot.properties.observationDate.monthValue == timestamp.Month &&
		   		hotspot.properties.observationDate.dayOfMonth == timestamp.Day
			) {
				hotspots.features.push(hotspot);
			}
		}
	}

	let item = new Object();
	item.Date = key;
	item.Data = hotspots;

	allDays.push(item)
	allDays.sort(sortByKey);
	
	return hotspots;
}

function findNextDate(type)
{
	let date = new Date("2030-01-01");


	for(let i = 0 ; i<migrationHotspots.features.length; i++)
	{
		let hotspot = migrationHotspots.features[i];
		let nowDate = new Object();

		if(type == "migration")
		{
			nowDate.Year  = hotspot.properties.fromHotspot.observationDate.year;
			nowDate.Month = hotspot.properties.fromHotspot.observationDate.monthValue;
			nowDate.Day   = hotspot.properties.fromHotspot.observationDate.dayOfMonth;

		} else {
			nowDate.Year  = hotspot.properties.observationDate.year;
			nowDate.Month = hotspot.properties.observationDate.monthValue;
			nowDate.Day   = hotspot.properties.observationDate.dayOfMonth;
		}
		
		let curDate = nowDate.Year + "-" + nowDate.Month + "-" + nowDate.Day;
		let newDate = new Date(curDate);

		if(newDate < date && currentDate < newDate){
			date = newDate;
		}

		if( type == "migration")
		{
			nowDate.Year  = hotspot.properties.toHotspot.observationDate.year;
			nowDate.Month = hotspot.properties.toHotspot.observationDate.monthValue;
			nowDate.Day   = hotspot.properties.toHotspot.observationDate.dayOfMonth;

		} else {
			nowDate.Year  = hotspot.properties.observationDate.year;
			nowDate.Month = hotspot.properties.observationDate.monthValue;
			nowDate.Day   = hotspot.properties.observationDate.dayOfMonth;
		}

		curDate = nowDate.Year + "-" + nowDate.Month + "-" + nowDate.Day;
		newDate = new Date(curDate);

		if(newDate < date && currentDate < newDate){
			date = newDate;
		}
	}

	return date;
}


function findEarliesDate(data, type)
{

	let date = new Date("2030-01-01");

	for(let i = 0 ; i<data.features.length; i++)
	{
		let hotspot = data.features[i];
		let currentDate = new Object();

		if( type == "migration")
		{
			currentDate.Year  = hotspot.properties.fromHotspot.observationDate.year;
			currentDate.Month = hotspot.properties.fromHotspot.observationDate.monthValue;
			currentDate.Day   = hotspot.properties.fromHotspot.observationDate.dayOfMonth;

		} else {
			currentDate.Year  = hotspot.properties.observationDate.year;
			currentDate.Month = hotspot.properties.observationDate.monthValue;
			currentDate.Day   = hotspot.properties.observationDate.dayOfMonth;
		}
		
		let curDate = currentDate.Year + "-" + currentDate.Month + "-" + currentDate.Day;
		let newDate = new Date(curDate);

		if(newDate < date){
			date = newDate;
		}
	}

	return date;
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function findNextStep(date)
{
	tempSpots = getHotspotstsByTime(allHotspots,date,'hostpot');
	
	if(tempSpots.features.length == 0){
		skip = true;
		return;
	} else{
		skip = false;
	}

	for(let j = 0; j<tempSpots.features.length; j++)
	{
		let spotId = tempSpots.features[j].properties.hotspotId;
		fromId = findHotspotFromById(spotId);
		if(!fromId) {
			currentHotspots.features.push(tempSpots.features[j]);
		} else {
			removeHotspotById(fromId);
			currentHotspots.features.push(tempSpots.features[j]);
		}
	}
}

function findHotspotFromById(id)
{
	for(let i = 0 ; i<migrationHotspots.features.length; i++)
	{
		let hotspot = migrationHotspots.features[i];
		if(id == hotspot.properties.toHotspot.hotspotId)
		{
			return hotspot.properties.fromHotspot.hotspotId;
		}
	}

	return false;
}

function removeHotspotById(id)
{
	for(let i = 0 ; i<currentHotspots.features.length; i++)
	{
		let hotspot = currentHotspots.features[i];
		if(id == hotspot.properties.hotspotId){
			currentHotspots.features[i].properties.trail = true;
			//currentHotspots.features.splice(i, 1);
		}
	}
}


