import cloneDeep from "lodash/cloneDeep";

export function sortingManager(strategy, services) {
  const serviceRoot = cloneDeep(services);
  sortByAlphabet(serviceRoot.services, 'name');
  switch (strategy) {
    case 'groupByTag':
    {
      groupByTagStrategy(serviceRoot);
      break;
    }
    case 'groupByService':
    {
      groupByServiceStrategy(serviceRoot);
      groupBySortByProperty(serviceRoot.groupedTagOverService,'service_tag');
      break;
    }
    default:
      break;
  }
  return serviceRoot;
}

//scenario 1
function groupByServiceStrategy(serviceRoot) {
  serviceRoot['groupedTagOverService'] = {};
  let groupedTagOverService = serviceRoot['groupedTagOverService'];

  while (serviceRoot.services.length > 0) {

    let splicedService = serviceRoot.services.splice(0, 1)[0];
    if (groupedTagOverService[splicedService.name] === undefined)
      groupedTagOverService[splicedService.name] = [];

    let index = groupedTagOverService[splicedService.name].findIndex(x=>x.service_tag === splicedService.service_tag);
    if (index ===-1)
      groupedTagOverService[splicedService.name].push(splicedService)
    else
      groupedTagOverService[splicedService.name][index].nodes.push(...splicedService.nodes);
  }
}

//scenario 2
function groupByTagStrategy(serviceRoot) {
  serviceRoot['groupedServicesOverTag'] = {};
  let groupedServicesOverTag = serviceRoot.groupedServicesOverTag;


  //sort keys before creating properties, this makes the object sorted when looping through
  const uniques = [...new Set(serviceRoot.services.map(item => item.service_tag))];
  uniques.sort();
  for (let i = 0; i < uniques.length; i++) {
    let unique = uniques[i];
    let tagName = unique.length > 0 ? unique : 'NO TAG';
    if (groupedServicesOverTag[tagName] === undefined)
      groupedServicesOverTag[tagName] = [];
  }

  while (serviceRoot.services.length > 0) {
    let service = serviceRoot.services[0];
    let tagName = service.service_tag.length > 0 ? service.service_tag : 'NO TAG';
    let splicedService = serviceRoot.services.splice(0, 1)[0];
    groupedServicesOverTag[tagName].push(splicedService)
  }
}


function groupBySortByProperty(services,index){

  for (const key in services) {
    if (services.hasOwnProperty(key)) {
      sortByAlphabet(services[key],index)
    }
  }
}
function sortByAlphabet(services, key) {
  return services.sort(function (a, b) {
    const serviceA = a[key].toUpperCase();
    const serviceB = b[key].toUpperCase();
    return (serviceA < serviceB) ? -1 : (serviceA > serviceB) ? 1 : 0;
  });
}
