function conversionHighChat(){
	//converted leads
        Highcharts.chart('convertedLeads', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 200,
            },
            colors: ['#ff9393'],
            credits: false,
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
                title: {
                    text: "Converted Leads - Last 30 days"
                },
                lineColor: 'transparent',
                min: 0,
                max: 29,
                tickLength: 0,
                labels: {
                    rotation: 0,
                    style: {
                        fontSize: '10px',
                        color: '#cccccc'
                    }
                }
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            legend: {
                enabled: false
            },
            tooltip: {
                enabled: false
            },
            series: [{
                name: 'IPS',
                data: [
                    ['1', 2],
                    ['2', 3],
                    ['3', 1],
                    ['4', 0],
                    ['5', 0],
                    ['6', 2],
                    ['7', 1],
                    ['8', 1],
                    ['9', 5],
                    ['10', 3],
                    ['11', 1],
                    ['12', 0],
                    ['13', 2],
                    ['14', 0],
                    ['15', 4],
                    ['16', 1],
                    ['17', 0],
                    ['18', 2],
                    ['19', 1],
                    ['20', 3],
                    ['21', 2],
                    ['22', 3],
                    ['23', 1],
                    ['24', 0],
                    ['25', 0],
                    ['26', 2],
                    ['27', 1],
                    ['28', 1],
                    ['29', 5],
                    ['30', 3],

                ],
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    align: 'right',
                    y: 0, // 10 pixels down from the top
                    style: {
                        fontSize: '10px'

                    }
                }
            }]
        });
  //lead to opportunity ratio
        Highcharts.chart('leadOpportunityRatio', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 140,
                // margin: 0
            },
            colors: ['#003961'],
            title: {
                text: '22%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 80,
                margin: 0,
                style: { "fontSize": '16', "color": '#453f7a' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#8077cb',
                    thickness: 25
                }, {
                    from: 22,
                    to: 70,
                    color: '#d9d9d9',
                    thickness: 25
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#00f7ff',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '0%',
                    // backgroundColor: '#730101',
                    // topWidth: 7,
                    // baseWidth: 1,
                    // baseLength: '0%',
                    // rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });		
//opportunity to win ratio
      Highcharts.chart('opportunityWinRatio', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 140,
                // margin: 0
            },
            colors: ['#003961'],
            title: {
                text: '32%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 80,
                margin: 0,
                style: { "fontSize": '16', "color": '#453f7a' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 40,
                    color: '#8077cb',
                    thickness: 25
                }, {
                    from: 40,
                    to: 80,
                    color: '#d9d9d9',
                    thickness: 25
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '0%',
                    // backgroundColor: '#730101',
                    // topWidth: 7,
                    // baseWidth: 1,
                    // baseLength: '0%',
                    // rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });

      //conversion rate
        Highcharts.chart('conversionRate', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 58,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#003256',
            }],

        });
        //conversion rate 1
        Highcharts.chart('conversionRate1', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 58,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#003256',
            }],

        });
        //conversion rate 2
        Highcharts.chart('conversionRate2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 58,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#003256',
            }],

        });
}