function recruitmentHighChat(){
	  //recruitment conversion rate 1
        Highcharts.chart('recruitmentConversionRate1', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 150,
            },
            title: {
                text: 'Recruitment Conversion Rate',
                style: { fontSize: '12px', },
                align: 'left'
            },
            subtitle: {
                text: '12%',
                style: { fontSize: '15px', fontWeight: 'bold', },
                verticalAlign: 'bottom',
                align: 'right'
            },

            credits: {
                enabled: false
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
            },
            yAxis: {
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
                showInLegend: false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: false,
                    enableMouseTracking: false
                }
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#00536e',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //recruitment conversion rate 2
        Highcharts.chart('recruitmentConversionRate2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 150,
            },
            title: {
                text: 'Recruitment Conversion Rate',
                style: { fontSize: '12px', },
                align: 'left'
            },
            subtitle: {
                text: '12%',
                style: { fontSize: '15px', fontWeight: 'bold', },
                verticalAlign: 'bottom',
                align: 'right'
            },

            credits: {
                enabled: false
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
            },
            yAxis: {
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
                showInLegend: false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: false,
                    enableMouseTracking: false
                }
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#00536e',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //recruitment conversion rate 2
        Highcharts.chart('recruitmentConversionRate3', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 150,
            },
            title: {
                text: 'Recruitment Conversion Rate',
                style: { fontSize: '12px', },
                align: 'left'
            },
            subtitle: {
                text: '12%',
                style: { fontSize: '15px', fontWeight: 'bold', },
                verticalAlign: 'bottom',
                align: 'right'
            },

            credits: {
                enabled: false
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
            },
            yAxis: {
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
                showInLegend: false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: false,
                    enableMouseTracking: false
                }
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2,
                    16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#00536e',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //Avg. Cost of Hiring by Seniority Level
        Highcharts.chart('avgCostHiringSeniorityLevel', {
            chart: {
                type: 'pie',
                animation: true,
                height: 150
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            plotOptions: {
                pie: {
                    shadow: false
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
                }
            },
            series: [{
                name: '',
                data: [["Junior", 43], ["Mid-Level", 25], ["Senior", 32]],
                size: '50%',
                innerSize: '45%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    format: '{point.percentage:.1f} %'
                }
            }]
        });
        //Avg. Time To Fill By Department
        Highcharts.chart('avgTimeToFillByDepartment', {
            chart: {
                animated: true,
                height: 247.5
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
                categories: ['Customer Service', 'Engineering & Data Science', 'HR, Finance & Accounting', 'Marketing', 'Product Management & Design', 'Sales']
            },
            yAxis: {
                title: {
                    text: ''
                }
            },

            series: [{
                type: 'column',
                name: 'Actual',
                data: [59, 83, 65, 228, 59, 83],
                color: '#00cddb',
            }, {
                name: 'Forecast',
                color: 'transparent',
                lineColor: 'transparent',
                data: [47, 83.33, 70.66, 239.33, 47, 83.33],
                type: 'spline',
                dataLabels: {
                    enabled: false,
                },
                marker: {
                    lineWidth: 2,
                    fillColor: '#005c9f'
                }
            },]
        });
	
	
        //Turnover Rate By Age Group
        Highcharts.chart('turnoverRateByAgeGroup', {
            chart: {
                type: 'column',
                animation: true,
                height: 263,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                categories: ['< 25', '26-35', '36-45', '46-54', '> 55'],

            },
            yAxis: {
                title: {
                    text: ''
                },
                format: '{y} %',
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        format: '{y} %',
                        color: '#ffffff'
                    }

                }
            },

            series: [{
                name: 'Voluntary Loss',
                data: [74, 73, 47, 53, 67],
                color: '#2596be'
            }, {
                name: 'Involuntary Loss',
                data: [18, 14, 35, 10, 21],
                color: '#d05555'
            }]
        });
}