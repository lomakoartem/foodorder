'use strict';

App.controller('FoodController', [ '$scope', 'FoodService',
		function($scope, FoodService) {
			var self = this;
			self.food = [];

			self.fetchAllFood = function() {
				FoodService.fetchAllFood().then(function(d) {
					self.food = d;
				}, function(errResponse) {
					console.error('Error while fetching food');
				});
			};

			self.fetchAllFood();
		} ]);