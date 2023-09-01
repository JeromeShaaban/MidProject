angular.module('favoriteApp', [])
    .controller('FavoritesController', function($scope, $http) {

        $scope.categories = [];
        $scope.realCategories = [];
        $scope.favorites = [];

        $scope.filter = {
            category: 0
        };

        $scope.mode = 'view';

        $scope.favorite = {};

        $scope.category = {};

        $scope.setMode = function(text) {
            if (text === 'favoriteCreation') {
                $scope.realCategories = $scope.categories.filter(function(c) { return c.id !== 0 });
                var idx = $scope.realCategories.map(function(c) { return c.id }).indexOf($scope.filter.category);
                if (idx < 0) idx = 0;

                $scope.favorite = {
                    link: '',
                    category: $scope.realCategories[idx].id
                }
            }

            if (text === 'favoriteEdition') {
                $scope.realCategories = $scope.categories.filter(function(c) { return c.id !== 0 });
            }

            if (text === 'categoryCreation') {
                $scope.category = {
                    label: ''
                }
            }

            if (text === 'categoryEdition') {
                $scope.realCategories = $scope.categories.filter(function(c) { return c.id !== 0 });
                $scope.category = {
                    label: ''
                }
            }

            $scope.mode = text;
        }

        $scope.cancel = function() {
            $scope.setMode('view');
        }

        $scope.edit = function(f) {
        $scope.favorite = {id: f.id, link: f.link, name: f.name, category: f.categoryDto.id};
        $scope.setMode('favoriteEdition');
        }

        // Pour voir toutes les URL de CRUD définies dans le back : http://localhost:8080/swagger-ui/index.html

        $scope.validate = function() {
                $http.post('api/category/' + $scope.favorite.category + '/favorites' , { id: $scope.favorite.id, link: $scope.favorite.link, name: $scope.favorite.name })
                .then(
                    function() {
                        $scope.refresh();
                        $scope.setMode('view');
                        showAlert('Favorite successfully Added', 3000, 'success', 'fade-in-out', '1s', 'bottom');
                    },
                    function(error) {
                        showAlert(error.data.message, 3000, 'danger', 'fade-in-out', '1s', 'bottom');
                    }
                )
        }

        $scope.validateCategory = function() {
                $http.post('api/category/all' , { id: $scope.category.id, label: $scope.category.label })
                .then(
                    function() {
                        $scope.refresh();
                        $scope.setMode('view');
                        showAlert('Category successfully Added', 3000, 'success', 'fade-in-out', '1s', 'bottom');
                    },
                    function(error) {
                        showAlert(error.data.message, 3000, 'danger', 'fade-in-out', '1s', 'bottom');
                    }
                )
        }

        $scope.deletion = function(id) {
        Swal.fire({
          title: 'Are you sure?',
          text: "You won't be able to revert this!",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (result.isConfirmed) {
            $http.delete('api/favorites/' + id ).then(
                function() {
                   $scope.refresh();
                },
                function(error) {
                   alert(error.data.message);
                }
            )
            Swal.fire(
              'Deleted!',
              'Your file has been deleted.',
              'success'
            )
          }
        })
        }

        $scope.refresh = function() {
            $http.get('api/category/all').then(
                function(response) {
                    $scope.categories = [{id: 0, label: "Everything", references: 0}];
                    response.data.forEach(d => {
                        $scope.categories.push(d);
                    })

                    $http.get('api/favorites').then(
                        function(response) {

                        console.log(response);

                            $scope.favorites = response.data.filter(f => $scope.filter.category === 0 || $scope.filter.category === f.categoryDto.id);

                        }
                    )
                }
            )
        }

        $scope.refresh();
    });