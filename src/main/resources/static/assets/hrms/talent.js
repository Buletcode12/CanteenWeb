function talentHighchart(){
	 //Talent Turnover Rate
        Highcharts.chart('talentTurnoverRate', {
            chart: {
                type: 'bar',
                zoomType: 'xy',
                animation: true,
                height: 200
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
            xAxis: [{
                categories: ['Finance', 'HR', 'Marketing', 'IT'],
                crosshair: true,
                gridLineColor: 'transparent'
            }],
            yAxis: [{
                labels: {
                    enabled: false,
                },
                title: {
                    text: '',
                },
                lineColor: 'transparent',
                gridLineColor: 'transparent'
            }
            ],
            plotOptions: {
                column: {
                    pointPadding: 0,
                    borderWidth: 0
                }
            },
            legend: {
                enabled: true
            },
            series: [{
                name: 'Involuntary',
                data: [27.6, 28.8, 21.7, 34.1],
                color: '#3799b6',

            }, {
                name: 'Voluntary',
                data: [83.6, 78.8, 98.5, 93.3],
                color: '#fd5b56',
                // margin: '0',

            }]
        });
        //fired Talents
        Highcharts.chart('firedTalents', {
            chart: {
                animated: true,
                height: 200
            },
            title: {
                text: ''
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: { enabled: false, },
            xAxis: {
                categories: ['6 months', '1 year', '2 years', '5 years'],
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: { enabled: false, },
                lineColor: 'transparent',
                gridLineColor: 'transparent'
            },
            legend: { enabled: false, },

            series: [{
                type: 'bar',
                name: 'Actual',
                data: [59, 83, 65, 228],
                color: '#00cddb',
            }, {
                name: 'Forecast',
                color: 'transparent',
                lineColor: 'transparent',
                data: [47, 83.33, 70.66, 239.33],
                type: 'spline',
                dataLabels: {
                    enabled: true,
                    format: '{y}%'
                },
                marker: {
                    lineWidth: 2,
                    fillColor: '#005c9f'
                }
            },]
        });
        //talent satisfaction (nfs)
        Highcharts.chart('talentSatisfactionNFS', {
            chart: {
                type: 'areaspline',
                zoomType: 'xy',
                animation: true,
                height: 150
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

            xAxis: {
                categories: ['Jan', 'Feb', 'Mar'],
                allowDecimals: false,
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: { enabled: false, },
                gridLineColor: 'transparent'
            },
            legend: { enabled: false, },
            plotOptions: {
                area: {
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: [{
                name: 'USSR/Russia',
                data: [10, 8, 10]
            }]
        });
        //trend last 6 months
        Highcharts.chart('trendLastSixMonths', {
            chart: {
                type: 'gauge',
                height: 150,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#f57e7a'],
            title: {
                text: '68',
                verticalAlign: 'bottom',
            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -148,
                endAngle: 149.9,
                background: null,
                center: ['50%', '75%'],
                size: '100%'
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
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#f57e7a',
                    thickness: 16
                }, {
                    from: 22,
                    to: 30,
                    color: '#d9d9d9',
                    thickness: 16
                }, {
                    from: 30,
                    to: 70,
                    color: '#83f1ad',
                    thickness: 16
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
                    radius: '100%',
                    backgroundColor: '#8f3936',
                    topWidth: 1,
                    baseWidth: 7,
                    baseLength: '5%',
                    rearLength: '0%'
                },
                pivot: {
                    radius: 5,
                    backgroundColor: '#8f3936',
                },

            }]

        });
        //talent rating
        Highcharts.chart('talentRating', {
            chart: {
                type: 'column',
                height: 160,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
            },
            yAxis: {
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#61c3fe'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 30,
                data: [
                    ['Jan', 15],
                    ['Feb', 20],
                    ['Mar', 25],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //trend by category 6 months
        Highcharts.chart('trendByCategory6Months', {

            chart: {
                polar: true,
                type: 'line',
                height: 250
            },

            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            pane: {
                size: '80%'
            },

            xAxis: {
                categories: ['Commitment', 'Delivery', 'Effectivity', 'Knowledge', 'Skill Sett'],
                tickmarkPlacement: 'on',
                lineWidth: 0,
            },

            yAxis: {
                gridLineInterpolation: 'pentagon',
                lineWidth: 0,
                min: 0
            },


            legend: {
                enabled: false,
            },

            series: [{
                name: '',
                data: [43000, 19000, 60000, 35000, 17000],
                pointPlacement: 'on'
            }],

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        pane: {
                            size: '70%'
                        }
                    }
                }]
            }

        });
        //trend by category 1 year
        Highcharts.chart('trendByCategoryOneYear', {

            chart: {
                polar: true,
                type: 'line',
                height: 100,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            pane: {
                size: '80%'
            },

            xAxis: {
                categories: ['Commitment', 'Delivery', 'Effectivity', 'Knowledge', 'Skill Sett'],
                tickmarkPlacement: 'on',
                lineWidth: 0,
                labels: { enabled: false }
            },

            yAxis: {
                gridLineInterpolation: 'pentagon',
                lineWidth: 0,
                min: 0,
                labels: { enabled: false }
            },


            legend: {
                enabled: false,
            },

            series: [{
                name: '',
                data: [43000, 19000, 60000, 35000, 17000],
                pointPlacement: 'on'
            }],

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        pane: {
                            size: '70%'
                        }
                    }
                }]
            }

        });
        //trend by category 2 years
        Highcharts.chart('trendByCategoryTwoYears', {

            chart: {
                polar: true,
                type: 'line',
                height: 100,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            pane: {
                size: '80%'
            },

            xAxis: {
                categories: ['Commitment', 'Delivery', 'Effectivity', 'Knowledge', 'Skill Sett'],
                tickmarkPlacement: 'on',
                lineWidth: 0,
                labels: { enabled: false }
            },

            yAxis: {
                gridLineInterpolation: 'pentagon',
                lineWidth: 0,
                min: 0,
                labels: { enabled: false }
            },


            legend: {
                enabled: false,
            },

            series: [{
                name: '',
                data: [43000, 19000, 60000, 35000, 17000],
                pointPlacement: 'on'
            }],

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        pane: {
                            size: '70%'
                        }
                    }
                }]
            }

        });
        //trend by category 5 years
        Highcharts.chart('trendByCategoryFiveYears', {

            chart: {
                polar: true,
                type: 'line',
                height: 100,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            pane: {
                size: '80%'
            },

            xAxis: {
                categories: ['Commitment', 'Delivery', 'Effectivity', 'Knowledge', 'Skill Sett'],
                tickmarkPlacement: 'on',
                lineWidth: 0,
                labels: { enabled: false }
            },

            yAxis: {
                gridLineInterpolation: 'pentagon',
                lineWidth: 0,
                min: 0,
                labels: { enabled: false }
            },


            legend: {
                enabled: false,
            },

            series: [{
                name: '',
                data: [43000, 19000, 60000, 35000, 17000],
                pointPlacement: 'on'
            }],

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        pane: {
                            size: '70%'
                        }
                    }
                }]
            }

        });
	
}